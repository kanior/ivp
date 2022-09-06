package kanior.ivp.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kanior.ivp.dto.MovieListResponse;
import kanior.ivp.dto.MovieDetailResponse;
import kanior.ivp.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static kanior.ivp.entity.QActor.actor;
import static kanior.ivp.entity.QMovie.movie;
import static kanior.ivp.entity.QMovieActor.movieActor;

public class MovieRepositoryImpl implements MovieRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MovieRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<MovieListResponse> findPage(Pageable pageable) {
        List<Movie> fetch = queryFactory
                .selectFrom(movie)
                .offset(pageable.getOffset())
                .limit(4)
                .orderBy(movie.releaseDate.desc())
                .fetch();

        List<MovieListResponse> content = fetch.stream()
                .map(MovieListResponse::new)
                .collect(Collectors.toList());

        Long total = queryFactory
                .select(movie.count())
                .from(movie)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Optional<MovieDetailResponse> findMovieDetailById(Long id) {
        Movie selectedMovie = queryFactory
                .selectFrom(movie)
                .where(movie.id.eq(id))
                .fetchOne();

        if (selectedMovie == null) {
            return Optional.empty();
        }

        List<Actor> actorList = queryFactory
                .selectFrom(actor)
                .join(movieActor).on(movieActor.actor.id.eq(actor.id))
                .join(movie).on(movieActor.movie.id.eq(movie.id))
                .where(movie.id.eq(id))
                .fetch();

        return Optional.of(new MovieDetailResponse(selectedMovie, actorList));
    }
}

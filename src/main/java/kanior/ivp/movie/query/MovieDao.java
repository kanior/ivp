package kanior.ivp.movie.query;

import kanior.ivp.movie.command.domain.Movie;
import kanior.ivp.querydsl.repository.support.Querydsl4RepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static kanior.ivp.movie.command.domain.QMovie.movie;

@Transactional(readOnly = true)
@Repository
public class MovieDao extends Querydsl4RepositorySupport {

    public Page<Movie> findPage(Pageable pageable) {
        List<Movie> content = selectFrom(movie)
                .offset(pageable.getOffset())
                .limit(4)
                .orderBy(movie.releaseDate.desc())
                .fetch();

        Long total = select(movie.count())
                .from(movie)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    public List<Movie> findAll() {
        return selectFrom(movie)
                .fetch();
    }

    public Optional<Movie> findById(Long id) {
        return Optional.ofNullable(
                selectFrom(movie)
                .where(movie.id.eq(id))
                .fetchOne());
    }

}

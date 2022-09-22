package kanior.ivp.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kanior.ivp.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static kanior.ivp.entity.QMovie.movie;
import static kanior.ivp.entity.QScreeningSchedule.screeningSchedule;

public class MovieRepositoryImpl implements MovieRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MovieRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Page<Movie> findPage(Pageable pageable) {
        List<Movie> content = queryFactory
                .selectFrom(movie)
                .offset(pageable.getOffset())
                .limit(4)
                .orderBy(movie.releaseDate.desc())
                .fetch();

        Long total = queryFactory
                .select(movie.count())
                .from(movie)
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    public List<Movie> findAllJoinScreeningSchedule(LocalDateTime now) {
        return queryFactory
                .select(movie).distinct()
                .from(movie)
                .join(screeningSchedule).on(screeningSchedule.movie.eq(movie))
                .where(screeningSchedule.screeningDate.after(now))
                .fetch();
    }
}

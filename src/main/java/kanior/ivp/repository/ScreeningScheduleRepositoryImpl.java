package kanior.ivp.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kanior.ivp.entity.ScreeningSchedule;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static kanior.ivp.entity.QScreen.screen;
import static kanior.ivp.entity.QScreeningSchedule.screeningSchedule;

public class ScreeningScheduleRepositoryImpl implements ScreeningScheduleRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ScreeningScheduleRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<ScreeningSchedule> findAllByScreenIdAndScreeningDate(Long screenId, LocalDateTime screeningDate) {
        LocalDateTime startDateTime = LocalDateTime.of(screeningDate.getYear(), screeningDate.getMonth(), screeningDate.getDayOfMonth(), 0, 0);
        LocalDateTime endDateTime = startDateTime.plusDays(1);
        return queryFactory
                .selectFrom(screeningSchedule)
                .where(screeningSchedule.screen.id.eq(screenId)
                        .and(screeningSchedule.screeningDate.between(startDateTime, endDateTime)))
                .fetch();
    }

    @Override
    public List<ScreeningSchedule> findAllByMovieIdAndTheaterIdAndScreeningDate(Long movieId, Long theaterId, LocalDateTime screeningDate) {
        LocalDateTime startDateTime = LocalDateTime.of(screeningDate.getYear(), screeningDate.getMonth(), screeningDate.getDayOfMonth(), 0, 0);
        LocalDateTime endDateTime = startDateTime.plusDays(1);
        return queryFactory
                .selectFrom(screeningSchedule)
                .join(screeningSchedule.screen, screen).fetchJoin()
                .where(screeningSchedule.movie.id.eq(movieId)
                        .and(screen.theater.id.eq(theaterId))
                        .and(screeningSchedule.screeningDate.between(startDateTime, endDateTime)))
                .fetch();
    }
}

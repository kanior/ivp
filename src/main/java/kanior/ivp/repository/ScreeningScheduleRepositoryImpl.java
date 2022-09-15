package kanior.ivp.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kanior.ivp.entity.ScreeningSchedule;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static kanior.ivp.entity.QScreeningSchedule.screeningSchedule;

public class ScreeningScheduleRepositoryImpl implements ScreeningScheduleRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ScreeningScheduleRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<ScreeningSchedule> findByScreenIdAndScreeningDate(Long screenId, LocalDateTime date) {
        LocalDateTime startDateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0);
        LocalDateTime endDateTime = startDateTime.plusDays(1);
        return queryFactory
                .selectFrom(screeningSchedule)
                .where(screeningSchedule.screen.id.eq(screenId)
                        .and(screeningSchedule.screeningDate.between(startDateTime, endDateTime)))
                .fetch();
    }
}

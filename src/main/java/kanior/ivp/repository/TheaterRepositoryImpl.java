package kanior.ivp.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kanior.ivp.entity.Theater;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static kanior.ivp.entity.QScreen.screen;
import static kanior.ivp.entity.QScreeningSchedule.screeningSchedule;
import static kanior.ivp.entity.QTheater.theater;

public class TheaterRepositoryImpl implements TheaterRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public TheaterRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Theater> findAllJoinScreeningSchedule(LocalDateTime now) {
        return queryFactory
                .select(theater).distinct()
                .from(theater)
                .join(screen).on(screen.theater.eq(theater))
                .join(screeningSchedule).on(screeningSchedule.screen.eq(screen))
                .where(screeningSchedule.screeningDate.after(now))
                .fetch();
    }
}

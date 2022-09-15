package kanior.ivp.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kanior.ivp.entity.Screen;

import javax.persistence.EntityManager;

import java.util.List;

import static kanior.ivp.entity.QScreen.screen;

public class ScreenRepositoryImpl implements ScreenRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ScreenRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Integer countByTheaterId(Long theaterId) {
        return queryFactory
                .select(screen.count())
                .from(screen)
                .where(screen.theater.id.eq(theaterId))
                .fetchOne()
                .intValue();
    }

    @Override
    public Integer sumSeatingCapacityByTheaterId(Long theaterId) {
        return queryFactory
                .select(screen.seatingCapacity.sum())
                .from(screen)
                .where(screen.theater.id.eq(theaterId))
                .fetchOne();
    }

    @Override
    public List<Screen> findAllByTheaterId(Long theaterId) {
        return queryFactory
                .selectFrom(screen)
                .where(screen.theater.id.eq(theaterId))
                .fetch();
    }
}

package kanior.ivp.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import static kanior.ivp.entity.QScreen.screen;

public class ScreenRepositoryImpl implements ScreenRepositoryCustom{

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
                .fetchOne()
                .intValue();
    }
}

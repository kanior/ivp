package kanior.ivp.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kanior.ivp.entity.*;

import javax.persistence.EntityManager;
import java.util.List;

import static kanior.ivp.entity.QMovie.movie;
import static kanior.ivp.entity.QReservation.reservation;
import static kanior.ivp.entity.QScreen.screen;
import static kanior.ivp.entity.QScreeningSchedule.screeningSchedule;
import static kanior.ivp.entity.QTheater.theater;
import static kanior.ivp.entity.QUser.user;

public class ReservationRepositoryImpl implements ReservationRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ReservationRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Reservation> findAllByUserId(Long userId) {
        return queryFactory
                .select(reservation)
                .from(reservation)
                .join(reservation.user, user)
                .join(reservation.screeningSchedule, screeningSchedule).fetchJoin()
                .join(reservation.movie, movie).fetchJoin()
                .join(reservation.screen, screen).fetchJoin()
                .join(reservation.theater, theater).fetchJoin()
                .where(reservation.user.id.eq(userId))
                .orderBy(reservation.createdDate.desc())
                .fetch();
    }
}

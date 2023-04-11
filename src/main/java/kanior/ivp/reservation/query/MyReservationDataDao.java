package kanior.ivp.reservation.query;

import kanior.ivp.querydsl.repository.support.Querydsl4RepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kanior.ivp.movie.command.domain.QMovie.movie;
import static kanior.ivp.reservation.command.domain.QReservation.reservation;
import static kanior.ivp.screen.command.domain.QScreen.screen;
import static kanior.ivp.screeningschedule.command.domain.QScreeningSchedule.screeningSchedule;
import static kanior.ivp.theater.command.domain.QTheater.theater;
import static kanior.ivp.user.command.domain.QUser.user;

@Transactional(readOnly = true)
@Repository
public class MyReservationDataDao extends Querydsl4RepositorySupport {

    public List<MyReservationData> findAllByUserLoginId(String userLoginId) {
        return select(new QMyReservationData(reservation.id, reservation.createdDate, reservation.reserveCount, reservation.isCanceled, movie.name, movie.rating, movie.runningTime, theater.name, screeningSchedule.screeningDate, screen.name, screen.seatType))
                .from(reservation)
                .join(reservation.user, user)
                .join(reservation.screeningSchedule, screeningSchedule)
                .join(screeningSchedule.movie, movie)
                .join(screeningSchedule.screen, screen)
                .join(screen.theater, theater)
                .where(user.loginId.eq(userLoginId))
                .fetch();
    }
}

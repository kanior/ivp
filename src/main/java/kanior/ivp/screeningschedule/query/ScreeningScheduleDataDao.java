package kanior.ivp.screeningschedule.query;

import kanior.ivp.querydsl.repository.support.Querydsl4RepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static kanior.ivp.screen.command.domain.QScreen.screen;
import static kanior.ivp.screeningschedule.command.domain.QScreeningSchedule.screeningSchedule;

@Transactional(readOnly = true)
@Repository
public class ScreeningScheduleDataDao extends Querydsl4RepositorySupport {

    public List<ScreeningScheduleData> findAllByMovieIdAndTheaterIdBetweenStartTimeAndEndTime(Long movieId, Long theaterId, LocalDateTime startTime, LocalDateTime endTime) {
        return select(new QScreeningScheduleData(screeningSchedule.id, screeningSchedule.remainingSeat, screeningSchedule.screeningDate, screen.name, screen.seatType, screen.seatingCapacity, screen.seatPrice))
                .from(screeningSchedule)
                .join(screeningSchedule.screen, screen)
                .where(screeningSchedule.movie.id.eq(movieId)
                        .and(screen.theater.id.eq(theaterId))
                        .and(screeningSchedule.screeningDate.between(startTime, null))
                        .and(screeningSchedule.screeningDate.before(endTime))
                )
                .fetch();
    }
}

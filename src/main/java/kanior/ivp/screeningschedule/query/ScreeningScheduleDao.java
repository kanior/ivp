package kanior.ivp.screeningschedule.query;

import kanior.ivp.movie.command.domain.Movie;
import kanior.ivp.querydsl.repository.support.Querydsl4RepositorySupport;
import kanior.ivp.screeningschedule.command.domain.ScreeningSchedule;
import kanior.ivp.theater.command.domain.Theater;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static kanior.ivp.movie.command.domain.QMovie.movie;
import static kanior.ivp.screen.command.domain.QScreen.screen;
import static kanior.ivp.screeningschedule.command.domain.QScreeningSchedule.screeningSchedule;
import static kanior.ivp.theater.command.domain.QTheater.theater;

@Transactional(readOnly = true)
@Repository
public class ScreeningScheduleDao extends Querydsl4RepositorySupport {

    public List<Movie> findAllMoviesByScreeningDateAfterNow(LocalDateTime now) {
        return select(movie).distinct()
                .from(screeningSchedule)
                .join(screeningSchedule.movie, movie)
                .where(screeningSchedule.screeningDate.after(now))
                .fetch();
    }

    public List<Theater> findAllTheatersByScreeningDateAfterNow(LocalDateTime now) {
        return select(theater).distinct()
                .from(screeningSchedule)
                .join(screeningSchedule.screen, screen)
                .join(screen.theater, theater)
                .where(screeningSchedule.screeningDate.after(now))
                .fetch();
    }

    public List<ScreeningSchedule> findAllByScreenIdAndBetweenStartTimeAndEndTime(Long screenId, LocalDateTime startTime, LocalDateTime endTime) {
        return selectFrom(screeningSchedule)
                .where(screeningSchedule.screen.id.eq(screenId)
                        .and(screeningSchedule.screeningDate.between(startTime, endTime)))
                .fetch();
    }
}

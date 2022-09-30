package kanior.ivp.repository;

import kanior.ivp.entity.ScreeningSchedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningScheduleRepositoryCustom {

    List<ScreeningSchedule> findAllByScreenIdAndScreeningDate(Long screenId, LocalDateTime screeningDate);

    List<ScreeningSchedule> findAllByMovieIdAndTheaterIdAndScreeningDate(Long movieId, Long theaterId, LocalDateTime screeningDate);
}

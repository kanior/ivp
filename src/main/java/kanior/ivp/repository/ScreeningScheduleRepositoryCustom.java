package kanior.ivp.repository;

import kanior.ivp.entity.ScreeningSchedule;

import java.time.LocalDateTime;
import java.util.List;

public interface ScreeningScheduleRepositoryCustom {

    List<ScreeningSchedule> findByScreenIdAndScreeningDate(Long screenId, LocalDateTime date);
}

package kanior.ivp.repository;

import kanior.ivp.entity.ScreeningSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreeningScheduleRepository extends JpaRepository<ScreeningSchedule, Long>, ScreeningScheduleRepositoryCustom {
}

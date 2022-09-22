package kanior.ivp.repository;

import kanior.ivp.entity.Theater;

import java.time.LocalDateTime;
import java.util.List;

public interface TheaterRepositoryCustom {

    List<Theater> findAllJoinScreeningSchedule(LocalDateTime now);
}

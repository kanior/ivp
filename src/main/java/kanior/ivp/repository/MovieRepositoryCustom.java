package kanior.ivp.repository;

import kanior.ivp.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;


public interface MovieRepositoryCustom {

    Page<Movie> findPage(Pageable pageable);

    List<Movie> findAllJoinScreeningSchedule(LocalDateTime now);
}

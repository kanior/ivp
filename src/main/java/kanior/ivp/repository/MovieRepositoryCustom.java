package kanior.ivp.repository;

import kanior.ivp.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MovieRepositoryCustom {

    Page<Movie> findPage(Pageable pageable);
}

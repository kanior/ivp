package kanior.ivp.repository;

import kanior.ivp.dto.MovieListResponse;
import kanior.ivp.dto.MovieDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface MovieRepositoryCustom {

    Page<MovieListResponse> findPage(Pageable pageable);

    Optional<MovieDetailResponse> findMovieDetailById(Long id);
}

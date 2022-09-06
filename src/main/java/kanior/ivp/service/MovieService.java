package kanior.ivp.service;

import kanior.ivp.dto.MovieListResponse;
import kanior.ivp.dto.MovieDetailResponse;
import kanior.ivp.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public Page<MovieListResponse> findPage(Pageable pageable) {
        return movieRepository.findPage(pageable);
    }

    public MovieDetailResponse findMovieDetailById(Long id) {
        MovieDetailResponse movieDetailResponse = movieRepository.findMovieDetailById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 영화 정보가 존재하지 않습니다. id=" + id));

        return movieDetailResponse;
    }

}

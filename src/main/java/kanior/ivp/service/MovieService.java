package kanior.ivp.service;

import kanior.ivp.dto.MovieInfoResponse;
import kanior.ivp.dto.MovieListResponse;
import kanior.ivp.entity.Movie;
import kanior.ivp.repository.MovieRepository;
import kanior.ivp.repository.ScreeningScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public Page<MovieListResponse> findPage(Pageable pageable) {
        return movieRepository.findPage(pageable).map(MovieListResponse::new);
    }

    public MovieInfoResponse findInfoById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 영화 정보가 존재하지 않습니다. id=" + id));

        return new MovieInfoResponse(movie);
    }

    public List<MovieListResponse> findAll() {
        return movieRepository.findAll()
                .stream().map(MovieListResponse::new)
                .collect(Collectors.toList());
    }

    public List<MovieListResponse> findAllJoinScreeningSchedule(LocalDateTime now) {
        return movieRepository.findAllJoinScreeningSchedule(now)
                .stream().map(MovieListResponse::new)
                .collect(Collectors.toList());
    }

}

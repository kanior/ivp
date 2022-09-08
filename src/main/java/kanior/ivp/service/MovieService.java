package kanior.ivp.service;

import kanior.ivp.dto.MovieInfoResponse;
import kanior.ivp.dto.MovieListResponse;
import kanior.ivp.entity.Actor;
import kanior.ivp.entity.Movie;
import kanior.ivp.repository.ActorRepository;
import kanior.ivp.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ActorRepository actorRepository;

    public Page<MovieListResponse> findPage(Pageable pageable) {
        return movieRepository.findPage(pageable).map(MovieListResponse::new);
    }

    public MovieInfoResponse findInfoById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 영화 정보가 존재하지 않습니다. id=" + id));

        List<Actor> actorList = actorRepository.findAllByMovieId(id);

        return new MovieInfoResponse(movie, actorList);
    }

}

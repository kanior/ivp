package kanior.ivp.movie.ui;

import kanior.ivp.actor.command.domain.Actor;
import kanior.ivp.actor.query.ActorDao;
import kanior.ivp.movie.query.MovieDao;
import kanior.ivp.movie.ui.dto.MovieDetailResponse;
import kanior.ivp.movie.ui.dto.MovieListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/movie")
public class MovieController {

    private final MovieDao movieDao;
    private final ActorDao actorDao;

    @GetMapping("/list")
    public String readMovieList(@PageableDefault(size = 4) Pageable pageable, Model model) {
        model.addAttribute("moviePage", movieDao.findPage(pageable).map(MovieListResponse::new));
        return "movie/list";
    }

    @GetMapping("/detail/{id}")
    public String readMovieDetail(@PathVariable Long id, Model model) {
        model.addAttribute("movieDetail", getMovieDetail(id));
        model.addAttribute("actors", getActors(id));
        return "movie/detail";
    }

    private MovieDetailResponse getMovieDetail(Long id) {
        return new MovieDetailResponse(movieDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 영화 정보가 존재하지 않습니다. id = " + id)));
    }

    private String getActors(Long movieId) {
        return actorDao.findAllByMovieId(movieId)
                .stream().map(Actor::getName)
                .collect(Collectors.joining(", "));
    }
}

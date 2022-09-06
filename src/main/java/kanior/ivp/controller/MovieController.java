package kanior.ivp.controller;

import kanior.ivp.dto.MovieListResponse;
import kanior.ivp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/list")
    public String readList(@PageableDefault(size = 4) Pageable pageable, Model model) {
        model.addAttribute("movies", movieService.findPage(pageable));
        return "movie/list";
    }

    @GetMapping("/detail/{id}")
    public String readDetail(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.findMovieDetailById(id));
        return "movie/detail";
    }
}
package kanior.ivp.controller;

import kanior.ivp.dto.MovieListResponse;
import kanior.ivp.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest")
public class MovieRestController {

    private final MovieService movieService;

    @GetMapping("/list")
    public Page<MovieListResponse> findPage(Pageable pageable) {

        return movieService.findPage(pageable);
    }
}

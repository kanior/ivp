package kanior.ivp.controller;

import kanior.ivp.dto.ScreeningScheduleListResponse;
import kanior.ivp.service.ScreenService;
import kanior.ivp.service.ScreeningScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/screeningSchedule")
public class ScreeningScheduleApiController {

    private final ScreeningScheduleService screeningScheduleService;

    @GetMapping("/list")
    public List<ScreeningScheduleListResponse> readListByMovieIdAndTheaterIdAndScreeningDate(
            @RequestParam Long movieId,
            @RequestParam Long theaterId,
            @RequestParam String screeningDate) {

        return screeningScheduleService.findAllByMovieIdAndScreenIdAndScreeningDate(movieId, theaterId, LocalDateTime.parse(screeningDate));
    }
}

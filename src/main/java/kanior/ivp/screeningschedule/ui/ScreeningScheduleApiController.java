package kanior.ivp.screeningschedule.ui;

import kanior.ivp.screeningschedule.query.ScreeningScheduleDataDao;
import kanior.ivp.screeningschedule.ui.dto.ReservableScreeningScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/screeningSchedule")
public class ScreeningScheduleApiController {

    private final ScreeningScheduleDataDao screeningScheduleDataDao;

    @GetMapping("/list")
    public List<ReservableScreeningScheduleResponse> readList(@RequestParam Long movieId,
                                                              @RequestParam Long theaterId,
                                                              @RequestParam String screeningDate) {

        LocalDateTime date = LocalDateTime.parse(screeningDate);
        LocalDateTime startTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0);
        LocalDateTime endTime = startTime.plusDays(1);

        return getReservableScreeningScheduleList(movieId, theaterId, startTime, endTime);
    }

    private List<ReservableScreeningScheduleResponse> getReservableScreeningScheduleList(Long movieId, Long theaterId, LocalDateTime startTime, LocalDateTime endTime) {
        return screeningScheduleDataDao.findAllByMovieIdAndTheaterIdBetweenStartTimeAndEndTime(movieId, theaterId, startTime, endTime)
                .stream().map(ReservableScreeningScheduleResponse::new)
                .collect(Collectors.toList());
    }
}

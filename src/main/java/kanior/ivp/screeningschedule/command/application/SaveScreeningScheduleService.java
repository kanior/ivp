package kanior.ivp.screeningschedule.command.application;

import kanior.ivp.movie.command.domain.Movie;
import kanior.ivp.movie.command.domain.MovieRepository;
import kanior.ivp.screen.command.domain.Screen;
import kanior.ivp.screen.command.domain.ScreenRepository;
import kanior.ivp.screeningschedule.command.domain.ScreeningSchedule;
import kanior.ivp.screeningschedule.command.domain.ScreeningScheduleRepository;
import kanior.ivp.screeningschedule.query.ScreeningScheduleDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class SaveScreeningScheduleService {

    private final ScreeningScheduleDao screeningScheduleDao;
    private final MovieRepository movieRepository;
    private final ScreeningScheduleRepository screeningScheduleRepository;
    private final ScreenRepository screenRepository;

    @Transactional(readOnly = true)
    public boolean isScreeningScheduleExisted(Long screenId, Long movieId, LocalDateTime screeningDate) {
        return !screeningScheduleDao.findAllByScreenIdAndBetweenStartTimeAndEndTime(screenId, screeningDate, screeningDate.plusMinutes(getRunningTime(movieId) + 20))
                .isEmpty();
    }

    @Transactional
    public Long save(SaveScreeningScheduleRequest saveRequest) {

        Movie movie = movieRepository.findById(saveRequest.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("해당 영화 정보가 존재하지 않습니다. id=" + saveRequest.getMovieId()));
        Screen screen = screenRepository.findById(saveRequest.getScreenId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상영관 정보가 존재하지 않습니다. id=" + saveRequest.getScreenId()));

        return screeningScheduleRepository.save(ScreeningSchedule.builder()
                .remainingSeat(saveRequest.getRemainingSeat())
                .screeningDate(saveRequest.getScreeningDate())
                .movie(movie)
                .screen(screen)
                .build()).getId();
    }

    private Integer getRunningTime(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("해당 영화 정보가 존재하지 않습니다. id=" + movieId))
                .getRunningTime();
    }

}

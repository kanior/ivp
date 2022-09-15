package kanior.ivp.service;

import kanior.ivp.dto.ScreeningScheduleSaveRequest;
import kanior.ivp.entity.Movie;
import kanior.ivp.entity.Screen;
import kanior.ivp.entity.ScreeningSchedule;
import kanior.ivp.repository.MovieRepository;
import kanior.ivp.repository.ScreenRepository;
import kanior.ivp.repository.ScreeningScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ScreeningScheduleService {

    private final ScreeningScheduleRepository screeningScheduleRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;

    public boolean isScreeningScheduleDuplicated(ScreeningScheduleSaveRequest schedule) {
        List<ScreeningSchedule> list = screeningScheduleRepository.findByScreenIdAndScreeningDate(schedule.getScreenId(), LocalDateTime.parse(schedule.getScreeningDate()));

        Movie movie = movieRepository.findById(schedule.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("해당 영화 정보가 존재하지 않습니다. id=" + schedule.getMovieId()));

        int startTime = schedule.getScreeningHour() * 60 + schedule.getScreeningMin();
        int endTime = startTime + movie.getRunningTime() + 20;

        //해당 상영관에 겹치는 상영 스케줄이 있는지 체크
        for (ScreeningSchedule screeningSchedule : list) {
            int s = screeningSchedule.getScreeningDate().getHour() * 60 + screeningSchedule.getScreeningDate().getMinute();
            int e = s + screeningSchedule.getMovie().getRunningTime() + 20;
            if (!(e <= startTime || endTime < s)) {
                return true;
            }
        }

        return false;
    }

    @Transactional
    public Long save(ScreeningScheduleSaveRequest form) {
        Movie movie = movieRepository.findById(form.getMovieId())
                .orElseThrow(() -> new IllegalArgumentException("해당 영화 정보가 존재하지 않습니다. id=" + form.getMovieId()));

        Screen screen = screenRepository.findById(form.getScreenId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상영관 정보가 존재하지 않습니다. id=" + form.getScreenId()));

        return screeningScheduleRepository.save(form.toEntity(movie, screen)).getId();
    }
}

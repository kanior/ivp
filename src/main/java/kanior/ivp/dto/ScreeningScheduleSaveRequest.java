package kanior.ivp.dto;

import kanior.ivp.entity.Movie;
import kanior.ivp.entity.Screen;
import kanior.ivp.entity.ScreeningSchedule;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class ScreeningScheduleSaveRequest {

    @NotNull
    private Long movieId;

    @NotNull
    private Long theaterId;

    @NotNull
    private Long screenId;

    @NotNull
    private String screeningDate;

    @NotNull
    private Integer screeningHour;

    @NotNull
    private Integer screeningMin;

    @NotNull
    private Integer remainingSeat;

    public ScreeningSchedule toEntity(Movie movie, Screen screen) {
        LocalDateTime date = LocalDateTime.parse(screeningDate);
        return ScreeningSchedule.builder()
                .movie(movie)
                .screen(screen)
                .screeningDate(LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), screeningHour, screeningMin))
                .remainingSeat(this.remainingSeat)
                .build();
    }
}

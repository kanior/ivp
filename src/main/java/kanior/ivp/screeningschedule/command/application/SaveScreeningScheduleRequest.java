package kanior.ivp.screeningschedule.command.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SaveScreeningScheduleRequest {

    private Integer remainingSeat;

    private LocalDateTime screeningDate;

    private Long movieId;

    private Long screenId;
}

package kanior.ivp.screeningschedule.ui.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SaveScreeningScheduleForm {

    @NotNull
    private Long movieId;

    @NotNull
    private Long theaterId;

    @NotNull
    private Long screenId;

    @NotNull
    private String screeningDate;

    @NotNull
    private String screeningHour;

    @NotNull
    private String screeningMin;

    @NotNull
    private Integer remainingSeat;

}

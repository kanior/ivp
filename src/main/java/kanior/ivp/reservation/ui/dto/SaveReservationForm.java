package kanior.ivp.reservation.ui.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SaveReservationForm {

    @NotNull
    private Long screeningScheduleId;

    @NotNull
    private Integer reserveCount;
}

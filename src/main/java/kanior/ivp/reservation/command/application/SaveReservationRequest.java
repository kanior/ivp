package kanior.ivp.reservation.command.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveReservationRequest {

    private String userLoginId;

    private Long screeningScheduleId;

    private Integer reserveCount;
}

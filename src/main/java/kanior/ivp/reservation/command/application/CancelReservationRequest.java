package kanior.ivp.reservation.command.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CancelReservationRequest {

    private Long reservationId;

    private String userLoginId;
}

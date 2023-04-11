package kanior.ivp.reservation.ui.dto;

import kanior.ivp.theater.command.domain.Theater;
import lombok.Getter;

@Getter
public class ReservableTheaterResponse {

    private Long id;

    private String name;

    public ReservableTheaterResponse(Theater theater) {
        this.id = theater.getId();
        this.name = theater.getName();
    }
}

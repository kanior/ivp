package kanior.ivp.screen.ui.dto;

import kanior.ivp.screen.command.domain.Screen;
import lombok.Getter;

@Getter
public class ScreenListResponse {

    private Long id;

    private String name;

    private String seatType;

    private Integer seatingCapacity;

    private Integer seatPrice;

    public ScreenListResponse(Screen screen) {
        this.id = screen.getId();
        this.name = screen.getName();
        this.seatType = screen.getSeatType();
        this.seatingCapacity = screen.getSeatingCapacity();
        this.seatPrice = screen.getSeatPrice();
    }
}

package kanior.ivp.dto;

import kanior.ivp.entity.Screen;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ScreenListResponse {

    private Long id;

    private String name;

    private String seatType;

    private Integer seatingCapacity;

    private Integer seatPrice;

    @Builder
    public ScreenListResponse(Screen entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.seatType = entity.getSeatType();
        this.seatingCapacity = entity.getSeatingCapacity();
        this.seatPrice = entity.getSeatPrice();
    }
}

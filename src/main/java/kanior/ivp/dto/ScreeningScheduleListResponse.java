package kanior.ivp.dto;

import kanior.ivp.entity.Screen;
import kanior.ivp.entity.ScreeningSchedule;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScreeningScheduleListResponse {

    private Long id;

    private String screenName;

    private String screenSeatType;

    private Integer screenSeatingCapacity;

    private Integer screenSeatPrice;

    private Integer remainingSeat;

    private String screeningHour;

    private String screeningMinute;

    public ScreeningScheduleListResponse(ScreeningSchedule entity) {
        this.id = entity.getId();
        this.remainingSeat = entity.getRemainingSeat();
        this.screeningHour = String.format("%02d", entity.getScreeningDate().getHour());
        this.screeningMinute = String.format("%02d", entity.getScreeningDate().getMinute());
        this.screenName = entity.getScreen().getName();
        this.screenSeatType = entity.getScreen().getSeatType();
        this.screenSeatingCapacity = entity.getScreen().getSeatingCapacity();
        this.screenSeatPrice = entity.getScreen().getSeatPrice();
    }
}

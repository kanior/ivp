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

    public ScreeningScheduleListResponse(ScreeningSchedule screeningSchedule, Screen screen) {
        this.id = screeningSchedule.getId();
        this.remainingSeat = screeningSchedule.getRemainingSeat();
        this.screeningHour = String.format("%02d", screeningSchedule.getScreeningDate().getHour());
        this.screeningMinute = String.format("%02d", screeningSchedule.getScreeningDate().getMinute());
        this.screenName = screen.getName();
        this.screenSeatType = screen.getSeatType();
        this.screenSeatingCapacity = screen.getSeatingCapacity();
        this.screenSeatPrice = screen.getSeatPrice();
    }
}

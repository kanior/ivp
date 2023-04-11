package kanior.ivp.screeningschedule.ui.dto;

import kanior.ivp.screeningschedule.query.ScreeningScheduleData;
import lombok.Getter;

@Getter
public class ReservableScreeningScheduleResponse {

    private Long id;

    private String screenName;

    private String screenSeatType;

    private Integer screenSeatingCapacity;

    private Integer screenSeatPrice;

    private Integer remainingSeat;

    private String screeningHour;

    private String screeningMinute;

    public ReservableScreeningScheduleResponse(ScreeningScheduleData screeningScheduleData) {
        this.id = screeningScheduleData.getId();
        this.remainingSeat = screeningScheduleData.getRemainingSeat();
        this.screeningHour = String.format("%02d", screeningScheduleData.getScreeningDate().getHour());
        this.screeningMinute = String.format("%02d", screeningScheduleData.getScreeningDate().getMinute());
        this.screenName = screeningScheduleData.getScreenName();
        this.screenSeatType = screeningScheduleData.getScreenSeatType();
        this.screenSeatingCapacity = screeningScheduleData.getScreenSeatingCapacity();
        this.screenSeatPrice = screeningScheduleData.getScreenSeatPrice();
    }
}

package kanior.ivp.screeningschedule.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScreeningScheduleData {

    private Long id;

    private Integer remainingSeat;

    private LocalDateTime screeningDate;

    private String screenName;

    private String screenSeatType;

    private Integer screenSeatingCapacity;

    private Integer screenSeatPrice;

    @QueryProjection
    public ScreeningScheduleData(Long id, Integer remainingSeat, LocalDateTime screeningDate, String screenName, String screenSeatType, Integer screenSeatingCapacity, Integer screenSeatPrice) {
        this.id = id;
        this.remainingSeat = remainingSeat;
        this.screeningDate = screeningDate;
        this.screenName = screenName;
        this.screenSeatType = screenSeatType;
        this.screenSeatingCapacity = screenSeatingCapacity;
        this.screenSeatPrice = screenSeatPrice;
    }
}

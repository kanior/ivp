package kanior.ivp.reservation.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyReservationData {

    private Long reservationId;

    private LocalDateTime reservationCreatedDate;

    private Integer reservationReserveCount;

    private boolean reservationCanceled;

    private String movieName;

    private String movieRating;

    private Integer movieRunningTime;

    private String theaterName;

    private LocalDateTime screeningScheduleScreeningDate;

    private String screenName;

    private String screenSeatType;

    @QueryProjection
    public MyReservationData(Long reservationId, LocalDateTime reservationCreatedDate, Integer reservationReserveCount, boolean reservationCanceled, String movieName, String movieRating, Integer movieRunningTime, String theaterName, LocalDateTime screeningScheduleScreeningDate, String screenName, String screenSeatType) {
        this.reservationId = reservationId;
        this.reservationCreatedDate = reservationCreatedDate;
        this.reservationReserveCount = reservationReserveCount;
        this.reservationCanceled = reservationCanceled;
        this.movieName = movieName;
        this.movieRating = movieRating;
        this.movieRunningTime = movieRunningTime;
        this.theaterName = theaterName;
        this.screeningScheduleScreeningDate = screeningScheduleScreeningDate;
        this.screenName = screenName;
        this.screenSeatType = screenSeatType;
    }
}

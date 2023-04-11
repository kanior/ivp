package kanior.ivp.reservation.ui.dto;

import kanior.ivp.reservation.query.MyReservationData;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class MyReservationResponse {

    private Long reservationId;

    private String reserveDate;

    private Integer reserveCount;

    private String isCanceled;

    private String isStartedAlready;

    private String movieName;

    private String movieRating;

    private Integer movieRunningTime;

    private String theaterName;

    private String screeningDate;

    private String screenName;

    private String screenSeatType;


    public MyReservationResponse(MyReservationData myReservationData) {
        this.reservationId = myReservationData.getReservationId();
        this.reserveDate = myReservationData.getReservationCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.reserveCount = myReservationData.getReservationReserveCount();
        this.isCanceled = myReservationData.isReservationCanceled() ? "true" : "false";
        this.isStartedAlready = LocalDateTime.now().isAfter(myReservationData.getScreeningScheduleScreeningDate()) ? "true" : "false";
        this.movieName = myReservationData.getMovieName();
        this.movieRating = myReservationData.getMovieRating();
        this.movieRunningTime = myReservationData.getMovieRunningTime();
        this.theaterName = myReservationData.getTheaterName();
        this.screeningDate = myReservationData.getScreeningScheduleScreeningDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.screenName = myReservationData.getScreenName();
        this.screenSeatType = myReservationData.getScreenSeatType();
    }
}

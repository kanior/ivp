package kanior.ivp.dto;

import kanior.ivp.entity.Reservation;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class ReservationListResponse {

    private Long id;

    private String reserveDate;

    private Integer reserveCount;

    private String movieName;

    private String movieRating;

    private Integer movieRunningTime;

    private String theaterName;

    private String screeningDate;

    private String screenName;

    private String screenSeatType;

    public ReservationListResponse(Reservation entity) {
        this.id = entity.getId();
        this.reserveDate = entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.reserveCount = entity.getReserveCount();
        this.movieName = entity.getScreeningSchedule().getMovie().getName();
        this.movieRating = entity.getScreeningSchedule().getMovie().getRating();
        this.movieRunningTime = entity.getScreeningSchedule().getMovie().getRunningTime();
        this.theaterName = entity.getScreeningSchedule().getScreen().getTheater().getName();
        this.screeningDate = entity.getScreeningSchedule().getScreeningDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.screenName = entity.getScreeningSchedule().getScreen().getName();
        this.screenSeatType = entity.getScreeningSchedule().getScreen().getSeatType();
    }
}

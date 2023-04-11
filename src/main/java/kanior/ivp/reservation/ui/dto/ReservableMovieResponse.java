package kanior.ivp.reservation.ui.dto;

import kanior.ivp.movie.command.domain.Movie;
import lombok.Getter;

@Getter
public class ReservableMovieResponse {

    private Long id;

    private String name;

    private Integer runningTime;

    private String releaseDate;

    private String rating;

    public ReservableMovieResponse(Movie movie) {
        this.id = movie.getId();
        this.name = movie.getName();
        this.runningTime = movie.getRunningTime();
        this.releaseDate = String.format("%d.%02d.%02d", movie.getReleaseDate().getYear(), movie.getReleaseDate().getMonthValue(), movie.getReleaseDate().getDayOfMonth());
        this.rating = movie.getRating();
    }
}

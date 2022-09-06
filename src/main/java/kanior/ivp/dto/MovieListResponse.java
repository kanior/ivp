package kanior.ivp.dto;

import kanior.ivp.entity.Movie;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Arrays;

@Getter
public class MovieListResponse {

    private Long id;

    private String name;

    private Integer runningTime;

    private String releaseDate;

    private String rating;

    private String poster;

    public MovieListResponse(Movie movie) {
        this.id = movie.getId();
        this.name = movie.getName();
        this.runningTime = movie.getRunningTime();
        this.releaseDate = String.format("%d.%02d.%02d", movie.getReleaseDate().getYear(), movie.getReleaseDate().getMonthValue(), movie.getReleaseDate().getDayOfMonth());
        this.rating = movie.getRating();
        this.poster = movie.getPhotos().split("\\\\")[0];
    }
}

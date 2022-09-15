package kanior.ivp.dto;

import kanior.ivp.entity.Movie;
import lombok.Getter;

@Getter
public class MovieListResponse {

    private Long id;

    private String name;

    private Integer runningTime;

    private String releaseDate;

    private String rating;

    private String poster;

    public MovieListResponse(Movie entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.runningTime = entity.getRunningTime();
        this.releaseDate = String.format("%d.%02d.%02d", entity.getReleaseDate().getYear(), entity.getReleaseDate().getMonthValue(), entity.getReleaseDate().getDayOfMonth());
        this.rating = entity.getRating();
        this.poster = entity.getPhotos().split("\\\\")[0];
    }
}

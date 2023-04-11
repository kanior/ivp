package kanior.ivp.movie.ui.dto;

import kanior.ivp.movie.command.domain.Movie;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class MovieDetailResponse {

    private Long id;

    private String name;

    private Integer runningTime;

    private String releaseDate;

    private String rating;

    private String nation;

    private String summary;

    private String director;

    private String genre;

    private List<String> photos = new ArrayList<>();

    private String poster;

    public MovieDetailResponse(Movie movie) {
        this.id = movie.getId();
        this.name = movie.getName();
        this.director = movie.getDirector();
        this.genre = movie.getGenre();
        this.rating = movie.getRating();
        this.runningTime = movie.getRunningTime();
        this.nation = movie.getNation();
        this.releaseDate = movie.getReleaseDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.poster = movie.getPhotos().split("\\\\")[0];
        this.photos.addAll(Arrays.asList(movie.getPhotos().split("\\\\")));
        this.photos.remove(0);
        this.summary = movie.getSummary();
    }
}

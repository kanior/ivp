package kanior.ivp.dto;

import kanior.ivp.entity.Movie;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MovieInfoResponse {

    private Long id;

    private String name;

    private String director;

    private String genre;

    private String rating;

    private String runningTime;

    private String nation;

    private String releaseDate;

    private String poster;

    private List<String> photos;

    private String summary;

    public MovieInfoResponse(Movie entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.director = entity.getDirector();
        this.genre = entity.getGenre();
        this.rating = entity.getRating();
        this.runningTime = entity.getRunningTime().toString() + "분";
        this.nation = entity.getNation();
        this.releaseDate = String.format("%d.%02d.%02d", entity.getReleaseDate().getYear(), entity.getReleaseDate().getMonthValue(), entity.getReleaseDate().getDayOfMonth());
        this.poster = entity.getPhotos().split("\\\\")[0];
        this.photos = new ArrayList<>();
        String[] split = entity.getPhotos().split("\\\\");
        for (String s : split) {
            this.photos.add(s);
        }
        this.photos.remove(0);
        this.summary = entity.getSummary();
    }
}

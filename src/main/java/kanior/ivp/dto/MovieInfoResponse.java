package kanior.ivp.dto;

import kanior.ivp.entity.Actor;
import kanior.ivp.entity.Movie;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MovieInfoResponse {

    private Long id;

    private String name;

    private String director;

    private String actors;

    private String genre;

    private String rating;

    private String runningTime;

    private String nation;

    private String releaseDate;

    private String poster;

    private List<String> photos;

    private String summary;

    public MovieInfoResponse(Movie movie, List<Actor> actorList) {
        this.id = movie.getId();
        this.name = movie.getName();
        this.director = movie.getDirector();
        this.actors = "";
        for (Actor a : actorList) {
            this.actors += ", " + a.getName();
        }
        this.actors = this.actors.substring(2);
        this.genre = movie.getGenre();
        this.rating = movie.getRating();
        this.runningTime = movie.getRunningTime().toString() + "분";
        this.nation = movie.getNation();
        this.releaseDate = String.format("%d.%02d.%02d", movie.getReleaseDate().getYear(), movie.getReleaseDate().getMonthValue(), movie.getReleaseDate().getDayOfMonth());
        this.poster = movie.getPhotos().split("\\\\")[0];
        this.photos = new ArrayList<>();
        String[] split = movie.getPhotos().split("\\\\");
        for (String s : split) {
            this.photos.add(s);
        }
        this.photos.remove(0);
        this.summary = movie.getSummary();
    }
}

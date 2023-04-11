package kanior.ivp.movie.command.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private Integer runningTime;

    private LocalDateTime releaseDate;

    @Column(nullable = false)
    private String rating;

    private String nation;

    @Column(length = 1000)
    private String summary;

    private String director;

    private String genre;

    @Column(length = 1000)
    private String photos;

}

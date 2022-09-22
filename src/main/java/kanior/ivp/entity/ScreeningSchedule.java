package kanior.ivp.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class ScreeningSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screening_schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id", nullable = false)
    private Screen screen;

    @Column(nullable = false)
    private LocalDateTime screeningDate;

    @Column(nullable = false)
    private Integer remainingSeat;

    @Builder
    public ScreeningSchedule(Movie movie, Screen screen, LocalDateTime screeningDate, Integer remainingSeat) {
        this.movie = movie;
        this.screen = screen;
        this.screeningDate = screeningDate;
        this.remainingSeat = remainingSeat;
    }


    // 비즈니스 로직 //

    /**
     * 예매
     */
    public boolean reservation(int count) {
        if (remainingSeat < count) {
            return false;
        }
        remainingSeat -= count;
        return true;
    }
}

package kanior.ivp.screeningschedule.command.domain;

import kanior.ivp.movie.command.domain.Movie;
import kanior.ivp.screen.command.domain.Screen;
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
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id")
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

    public boolean isReservable(int count) {
        return remainingSeat >= count;
    }

    public boolean isCancelableNow(LocalDateTime now) {
        return screeningDate.isAfter(now);
    }

    public void reserve(int count) {
        if (!isReservable(count)) {
            throw new IllegalArgumentException("예매하려는 좌석 수가 잔여 좌석 수보다 많습니다.");
        }
        remainingSeat -= count;
    }

    public void cancel(int count) {
        remainingSeat += count;
    }
}

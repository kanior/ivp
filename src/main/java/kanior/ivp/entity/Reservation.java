package kanior.ivp.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screening_schedule_id", nullable = false)
    private ScreeningSchedule screeningSchedule;

    @Column(nullable = false)
    private Integer reserveCount;

    @Builder
    public Reservation(User user, ScreeningSchedule screeningSchedule, Integer reserveCount) {
        this.user = user;
        this.screeningSchedule = screeningSchedule;
        this.reserveCount = reserveCount;
    }
}

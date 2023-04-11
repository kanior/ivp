package kanior.ivp.reservation.command.domain;

import kanior.ivp.common.model.BaseTimeEntity;
import kanior.ivp.screeningschedule.command.domain.ScreeningSchedule;
import kanior.ivp.user.command.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Reservation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @Column(nullable = false)
    private Integer reserveCount;

    @Column(nullable = false)
    private boolean isCanceled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screening_schedule_id", nullable = false)
    private ScreeningSchedule screeningSchedule;

    @Builder
    public Reservation(Long id, Integer reserveCount, boolean isCanceled, User user, ScreeningSchedule screeningSchedule) {
        this.id = id;
        this.reserveCount = reserveCount;
        this.isCanceled = isCanceled;
        this.user = user;
        this.screeningSchedule = screeningSchedule;
    }

    public boolean isUserLoginIdCorrect(String userLoginId) {
        return user.isLoginIdCorrect(userLoginId);
    }

    public boolean isScreeningScheduleCancelableNow(LocalDateTime now) {
        return screeningSchedule.isCancelableNow(now);
    }

    public void cancel() {
        screeningSchedule.cancel(reserveCount);
        isCanceled = true;
    }

}

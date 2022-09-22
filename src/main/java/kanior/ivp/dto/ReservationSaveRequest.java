package kanior.ivp.dto;

import kanior.ivp.entity.Reservation;
import kanior.ivp.entity.ScreeningSchedule;
import kanior.ivp.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReservationSaveRequest {

    @NotBlank
    private String userLoginId;

    @NotNull
    private Long screeningScheduleId;

    @NotNull
    private Integer reserveCount;

    public Reservation toEntity(User user, ScreeningSchedule screeningSchedule) {
        return Reservation.builder()
                .user(user)
                .screeningSchedule(screeningSchedule)
                .reserveCount(this.reserveCount)
                .build();
    }
}

package kanior.ivp.reservation.command.application;

import kanior.ivp.reservation.command.domain.Reservation;
import kanior.ivp.reservation.command.domain.ReservationRepository;
import kanior.ivp.screeningschedule.command.domain.ScreeningSchedule;
import kanior.ivp.screeningschedule.command.domain.ScreeningScheduleRepository;
import kanior.ivp.user.command.domain.User;
import kanior.ivp.user.command.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SaveReservationService {

    private final UserRepository userRepository;
    private final ScreeningScheduleRepository screeningScheduleRepository;
    private final ReservationRepository reservationRepository;

    @Transactional(readOnly = true)
    public boolean isRemainingSeatEnough(Long screeningScheduleId, int reserveCount) {
        return screeningScheduleRepository.findById(screeningScheduleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상영 스케줄 정보가 존재하지 않습니다. id=" + screeningScheduleId))
                .isReservable(reserveCount);
    }

    @Transactional
    public Long save(SaveReservationRequest saveRequest) {
        User user = userRepository.findByLoginId(saveRequest.getUserLoginId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 정보가 존재하지 않습니다. loginId=" + saveRequest.getUserLoginId()));
        ScreeningSchedule screeningSchedule = screeningScheduleRepository.findById(saveRequest.getScreeningScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상영 스케줄 정보가 존재하지 않습니다. id=" + saveRequest.getScreeningScheduleId()));

        screeningSchedule.reserve(saveRequest.getReserveCount());

        return reservationRepository.save(Reservation.builder()
                .reserveCount(saveRequest.getReserveCount())
                .isCanceled(false)
                .user(user)
                .screeningSchedule(screeningSchedule)
                .build()).getId();
    }

}

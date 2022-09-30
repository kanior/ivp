package kanior.ivp.service;

import kanior.ivp.dto.ReservationSaveRequest;
import kanior.ivp.entity.Reservation;
import kanior.ivp.entity.ScreeningSchedule;
import kanior.ivp.entity.User;
import kanior.ivp.repository.ReservationRepository;
import kanior.ivp.repository.ScreeningScheduleRepository;
import kanior.ivp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final UserRepository userRepository;
    private final ScreeningScheduleRepository screeningScheduleRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public Long save(ReservationSaveRequest form) {
        User user = userRepository.findByLoginId(form.getUserLoginId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저 정보가 존재하지 않습니다. loginId=" + form.getUserLoginId()));

        ScreeningSchedule screeningSchedule = screeningScheduleRepository.findById(form.getScreeningScheduleId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상영 스케줄 정보가 존재하지 않습니다. id=" + form.getScreeningScheduleId()));

        if (!screeningSchedule.reservation(form.getReserveCount())) {
            return null;
        }

        return reservationRepository.save(form.toEntity(user, screeningSchedule)).getId();
    }

    @Transactional
    public void delete(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 상영 스케줄 정보가 존재하지 않습니다. id=" + id));

        reservation.getScreeningSchedule().cancel(reservation.getReserveCount());
        reservationRepository.delete(reservation);
    }
}

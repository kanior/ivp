package kanior.ivp.reservation.command.application;

import kanior.ivp.reservation.command.domain.Reservation;
import kanior.ivp.reservation.command.domain.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CancelReservationService {

    private final ReservationRepository reservationRepository;

    @Transactional
    public void cancel(CancelReservationRequest cancelRequest) {
        Reservation reservation = reservationRepository.findById(cancelRequest.getReservationId())
                .orElseThrow(() -> new IllegalArgumentException("해당 상영 스케줄 정보가 존재하지 않습니다. id=" + cancelRequest.getReservationId()));

        if (!reservation.isUserLoginIdCorrect(cancelRequest.getUserLoginId())) {
            throw new IllegalArgumentException("예매 취소 요청 사용자가 예매 사용자와 일치하지 않습니다.");
        }

        if (reservation.isCanceled()) {
            throw new IllegalArgumentException("이미 취소된 예매 내역입니다. id=" + cancelRequest.getReservationId());
        }

        reservation.cancel();
    }
}

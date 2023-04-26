package kanior.ivp.reservation.command.domain;

import kanior.ivp.screeningschedule.command.domain.ScreeningSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    void deleteByScreeningSchedule(ScreeningSchedule screeningSchedule);
}

package kanior.ivp.repository;

import kanior.ivp.entity.Reservation;

import java.util.List;

public interface ReservationRepositoryCustom {

    List<Reservation> findAllByUserId(Long userId);
}

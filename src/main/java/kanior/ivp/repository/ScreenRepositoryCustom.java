package kanior.ivp.repository;

import kanior.ivp.entity.Screen;

import java.util.List;

public interface ScreenRepositoryCustom {

    Integer countByTheaterId(Long theaterId);

    Integer sumSeatingCapacityByTheaterId(Long theaterId);

    List<Screen> findAllByTheaterId(Long theaterId);

}

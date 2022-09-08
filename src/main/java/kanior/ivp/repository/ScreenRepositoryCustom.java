package kanior.ivp.repository;

public interface ScreenRepositoryCustom {

    Integer countByTheaterId(Long theaterId);

    Integer sumSeatingCapacityByTheaterId(Long theaterId);
}

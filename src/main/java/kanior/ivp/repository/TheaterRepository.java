package kanior.ivp.repository;

import kanior.ivp.entity.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TheaterRepository extends JpaRepository<Theater, Long>, TheaterRepositoryCustom {
}

package kanior.ivp.repository;

import kanior.ivp.entity.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository extends JpaRepository<Screen, Long>, ScreenRepositoryCustom {
}

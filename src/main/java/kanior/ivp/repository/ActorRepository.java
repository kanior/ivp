package kanior.ivp.repository;

import kanior.ivp.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorRepository extends JpaRepository<Actor, Long>, ActorRepositoryCustom {
}

package kanior.ivp.repository;

import kanior.ivp.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long>, MovieRepositoryCustom {

    @Override
    Optional<Movie> findById(Long aLong);
}

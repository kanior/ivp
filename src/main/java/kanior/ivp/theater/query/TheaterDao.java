package kanior.ivp.theater.query;

import kanior.ivp.querydsl.repository.support.Querydsl4RepositorySupport;
import kanior.ivp.theater.command.domain.Theater;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static kanior.ivp.theater.command.domain.QTheater.theater;

@Transactional(readOnly = true)
@Repository
public class TheaterDao extends Querydsl4RepositorySupport {

    public List<Theater> findAll() {
        return selectFrom(theater)
                .fetch();
    }

    public Optional<Theater> findById(Long id) {
        return Optional.ofNullable(
                selectFrom(theater)
                .where(theater.id.eq(id))
                .fetchOne());
    }
}

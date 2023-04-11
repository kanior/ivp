package kanior.ivp.screen.query;

import kanior.ivp.querydsl.repository.support.Querydsl4RepositorySupport;
import kanior.ivp.screen.command.domain.Screen;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kanior.ivp.screen.command.domain.QScreen.screen;

@Transactional(readOnly = true)
@Repository
public class ScreenDao extends Querydsl4RepositorySupport {

    public List<Screen> findAllByTheaterId(Long theaterId) {
        return selectFrom(screen)
                .where(screen.theater.id.eq(theaterId))
                .fetch();
    }

    public Long countByTheaterId(Long theaterId) {
        return select(screen.count())
                .from(screen)
                .where(screen.theater.id.eq(theaterId))
                .fetchOne();
    }

    public Integer sumSeatingCapacityByTheaterId(Long theaterId) {
        return select(screen.seatingCapacity.sum())
                .from(screen)
                .where(screen.theater.id.eq(theaterId))
                .fetchOne();
    }
}

package kanior.ivp.actor.query;

import kanior.ivp.actor.command.domain.Actor;
import kanior.ivp.querydsl.repository.support.Querydsl4RepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kanior.ivp.actor.command.domain.QActor.actor;
import static kanior.ivp.movie.command.domain.QMovie.movie;
import static kanior.ivp.movieactor.domain.QMovieActor.movieActor;

@Transactional(readOnly = true)
@Repository
public class ActorDao extends Querydsl4RepositorySupport {

    public List<Actor> findAllByMovieId(Long movieId) {
        return selectFrom(actor)
                .join(movieActor).on(movieActor.actor.eq(actor))
                .join(movie).on(movieActor.movie.eq(movie))
                .where(movie.id.eq(movieId))
                .fetch();
    }
}

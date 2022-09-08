package kanior.ivp.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kanior.ivp.entity.Actor;

import javax.persistence.EntityManager;
import java.util.List;

import static kanior.ivp.entity.QActor.actor;
import static kanior.ivp.entity.QMovie.movie;
import static kanior.ivp.entity.QMovieActor.movieActor;

public class ActorRepositoryImpl implements ActorRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ActorRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Actor> findAllByMovieId(Long movieId) {
        return queryFactory
                .selectFrom(actor)
                .join(movieActor).on(movieActor.actor.id.eq(actor.id))
                .join(movie).on(movieActor.movie.id.eq(movie.id))
                .where(movie.id.eq(movieId))
                .fetch();
    }
}

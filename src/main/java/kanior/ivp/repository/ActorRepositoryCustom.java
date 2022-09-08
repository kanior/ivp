package kanior.ivp.repository;

import kanior.ivp.entity.Actor;

import java.util.List;

public interface ActorRepositoryCustom {

    List<Actor> findAllByMovieId(Long movieId);
}

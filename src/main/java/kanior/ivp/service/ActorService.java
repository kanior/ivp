package kanior.ivp.service;

import kanior.ivp.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ActorService {

    private final ActorRepository actorRepository;

    public List<String> findNamesByMovieId(Long movieId) {
        return actorRepository.findAllByMovieId(movieId)
                .stream().map(actor -> actor.getName())
                .collect(Collectors.toList());
    }
}

package kanior.ivp.reservation.command.application;

import kanior.ivp.redis.repository.RedisLockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SaveReservationFacade {

    private final RedisLockRepository redisLockRepository;
    private final SaveReservationService saveReservationService;

    public void save(SaveReservationRequest saveRequest) throws InterruptedException {
        String key = "saveReservation" + saveRequest.getScreeningScheduleId();
        while (!redisLockRepository.lock(key)) {
            Thread.sleep(100);
        }

        try {
            saveReservationService.save(saveRequest);
        } finally {
            redisLockRepository.unlock(key);
        }
    }
}

package kanior.ivp.reservation.command.application;

import kanior.ivp.reservation.command.domain.ReservationRepository;
import kanior.ivp.screeningschedule.command.domain.ScreeningSchedule;
import kanior.ivp.screeningschedule.command.domain.ScreeningScheduleRepository;
import kanior.ivp.user.command.domain.User;
import kanior.ivp.user.command.domain.UserRepository;
import kanior.ivp.user.command.domain.UserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SaveReservationServiceTest {

    @Autowired
    private SaveReservationFacade saveReservationFacade;

    @Autowired
    private ScreeningScheduleRepository screeningScheduleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void beforeEach() {
        userRepository.save(User.builder()
                .birthDate("111111")
                .name("테스트유저1")
                .loginId("testUser1")
                .password("testUser1")
                .role(UserRole.USER)
                .build());

        screeningScheduleRepository.saveAndFlush(ScreeningSchedule.builder()
                .screeningDate(LocalDateTime.now().plusWeeks(1L))
                .remainingSeat(5050)
                .build())
                .getId();
    }

    @AfterEach
    void afterEach() {
        reservationRepository.deleteAll();
        screeningScheduleRepository.deleteAll();
        userRepository.deleteAll();
    }


    @Test
    void 동시에_100개의_요청() throws InterruptedException, RuntimeException {
        Long id = screeningScheduleRepository.findAll().get(0).getId();
        int threadCount = 100;
        ExecutorService executor = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            int reserveCount = i + 1;
            executor.submit(() -> {
                try {
                    saveReservationFacade.save(new SaveReservationRequest("testUser1", id, reserveCount));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        ScreeningSchedule screeningSchedule = screeningScheduleRepository.findById(id).orElseThrow();
        assertEquals(0, screeningSchedule.getRemainingSeat());
    }
}
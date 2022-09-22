package kanior.ivp.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kanior.ivp.dto.ScreeningScheduleListResponse;
import kanior.ivp.entity.ScreeningSchedule;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static kanior.ivp.entity.QScreen.screen;
import static kanior.ivp.entity.QScreeningSchedule.screeningSchedule;

public class ScreeningScheduleRepositoryImpl implements ScreeningScheduleRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ScreeningScheduleRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<ScreeningSchedule> findAllByScreenIdAndScreeningDate(Long screenId, LocalDateTime screeningDate) {
        LocalDateTime startDateTime = LocalDateTime.of(screeningDate.getYear(), screeningDate.getMonth(), screeningDate.getDayOfMonth(), 0, 0);
        LocalDateTime endDateTime = startDateTime.plusDays(1);
        return queryFactory
                .selectFrom(screeningSchedule)
                .where(screeningSchedule.screen.id.eq(screenId)
                        .and(screeningSchedule.screeningDate.between(startDateTime, endDateTime)))
                .fetch();
    }

    @Override
    public List<ScreeningScheduleListResponse> findAllByMovieIdAndScreenIdAndScreeningDate(Long movieId, Long theaterId, LocalDateTime screeningDate) {
        LocalDateTime startDateTime = LocalDateTime.of(screeningDate.getYear(), screeningDate.getMonth(), screeningDate.getDayOfMonth(), 0, 0);
        LocalDateTime endDateTime = startDateTime.plusDays(1);
        List<Tuple> tupleList = queryFactory
                .select(screeningSchedule, screen)
                .from(screeningSchedule)
                .join(screen).on(screeningSchedule.screen.eq(screen))
                .where(screeningSchedule.movie.id.eq(movieId)
                        .and(screen.theater.id.eq(theaterId))
                        .and(screeningSchedule.screeningDate.between(startDateTime, endDateTime)))
                .fetch();

        if (tupleList.size() == 0) {
            return new ArrayList<>();
        }

        return tupleList.stream().map(tuple -> new ScreeningScheduleListResponse(tuple.get(screeningSchedule), tuple.get(screen)))
                .collect(Collectors.toList());
    }
}

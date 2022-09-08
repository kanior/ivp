package kanior.ivp.service;

import kanior.ivp.dto.TheaterInfoResponse;
import kanior.ivp.dto.TheaterListResponse;
import kanior.ivp.entity.Theater;
import kanior.ivp.repository.ScreenRepository;
import kanior.ivp.repository.TheaterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TheaterService {

    private final TheaterRepository theaterRepository;
    private final ScreenRepository screenRepository;

    public List<TheaterListResponse> findList() {
        return theaterRepository.findAll().stream()
                .map(TheaterListResponse::new)
                .collect(Collectors.toList());
    }

    public TheaterInfoResponse findInfoById(Long id) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 극장 정보가 존재하지 않습니다. id=" + id));

        Integer totalScreen = screenRepository.countByTheaterId(id);
        Integer totalSeat = screenRepository.sumSeatingCapacityByTheaterId(id);

        return new TheaterInfoResponse(theater, totalScreen, totalSeat);
    }
}

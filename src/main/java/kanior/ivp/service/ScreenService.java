package kanior.ivp.service;

import kanior.ivp.dto.ScreenListResponse;
import kanior.ivp.repository.ScreenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ScreenService {

    private final ScreenRepository screenRepository;

    public Integer findCountByTheaterId(Long theaterId) {
        return screenRepository.countByTheaterId(theaterId);
    }

    public Integer findSumSeatingCapacityByTheaterId(Long theaterId) {
        return screenRepository.sumSeatingCapacityByTheaterId(theaterId);
    }

    public List<ScreenListResponse> findAllByTheaterId(Long theaterId) {
        return screenRepository.findAllByTheaterId(theaterId)
                .stream().map(ScreenListResponse::new)
                .collect(Collectors.toList());
    }

}

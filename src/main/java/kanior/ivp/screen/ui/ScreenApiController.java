package kanior.ivp.screen.ui;

import kanior.ivp.screen.query.ScreenDao;
import kanior.ivp.screen.ui.dto.ScreenListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/screen")
public class ScreenApiController {

    private final ScreenDao screenDao;

    @GetMapping("/list/{theaterId}")
    public List<ScreenListResponse> readList(@PathVariable Long theaterId) {
        return screenDao.findAllByTheaterId(theaterId)
                .stream().map(ScreenListResponse::new)
                .collect(Collectors.toList());
    }
}

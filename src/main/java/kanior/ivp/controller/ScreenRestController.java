package kanior.ivp.controller;

import kanior.ivp.dto.ScreenListResponse;
import kanior.ivp.service.ScreenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/screen")
public class ScreenRestController {

    private final ScreenService screenService;

    @GetMapping("/list/{theaterId}")
    public List<ScreenListResponse> readListByTheaterId(@PathVariable Long theaterId) {
        return screenService.findAllByTheaterId(theaterId);
    }
}

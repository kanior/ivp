package kanior.ivp.theater.ui;

import kanior.ivp.screen.query.ScreenDao;
import kanior.ivp.theater.query.TheaterDao;
import kanior.ivp.theater.ui.dto.TheaterDetailResponse;
import kanior.ivp.theater.ui.dto.TheaterListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/theater")
public class TheaterController {

    private final TheaterDao theaterDao;
    private final ScreenDao screenDao;

    @GetMapping("/info/{id}")
    public String readInfo(@PathVariable Long id, Model model) {
        model.addAttribute("theaterList", getTheaterList());
        model.addAttribute("theaterDetail", getTheaterDetail(id));
        model.addAttribute("totalScreenNumber", screenDao.countByTheaterId(id));
        model.addAttribute("totalSeatNumber", screenDao.sumSeatingCapacityByTheaterId(id));
        return "theater/info";
    }

    private TheaterDetailResponse getTheaterDetail(Long id) {
        return new TheaterDetailResponse(theaterDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 극장 정보가 존재하지 않습니다. id = " + id)));
    }

    private List<TheaterListResponse> getTheaterList() {
        return theaterDao.findAll()
                .stream().map(TheaterListResponse::new)
                .collect(Collectors.toList());
    }
}

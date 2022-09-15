package kanior.ivp.controller;

import kanior.ivp.service.ScreenService;
import kanior.ivp.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/theater")
public class TheaterController {

    private final TheaterService theaterService;
    private final ScreenService screenService;

    @GetMapping("/info/{id}")
    public String readInfo(@PathVariable Long id, Model model) {
        model.addAttribute("theaterList", theaterService.findAll());
        model.addAttribute("theaterInfo", theaterService.findInfoById(id));
        model.addAttribute("totalScreenNumber", screenService.findCountByTheaterId(id));
        model.addAttribute("totalSeatNumber", screenService.findSumSeatingCapacityByTheaterId(id));
        return "theater/info";
    }
}

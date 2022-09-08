package kanior.ivp.controller;

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

    @GetMapping("/info/{id}")
    public String readInfo(@PathVariable Long id, Model model) {
        model.addAttribute("theaterList", theaterService.findList());
        model.addAttribute("theaterInfo", theaterService.findInfoById(id));
        return "theater/info";
    }
}

package kanior.ivp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/reservation")
public class ReservationController {


    @GetMapping("/save")
    public String saveForm(Model model) {

        return "reservation/saveForm";
    }
}

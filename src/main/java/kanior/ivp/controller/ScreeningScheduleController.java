package kanior.ivp.controller;

import kanior.ivp.dto.MovieListResponse;
import kanior.ivp.dto.ScreeningScheduleSaveRequest;
import kanior.ivp.service.MovieService;
import kanior.ivp.service.ScreeningScheduleService;
import kanior.ivp.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/screeningSchedule")
public class ScreeningScheduleController {

    private final MovieService movieService;
    private final TheaterService theaterService;
    private final ScreeningScheduleService screeningScheduleService;

    @GetMapping("/save")
    public String saveForm(Model model) {
        model.addAttribute("movieList", movieService.findAll());
        model.addAttribute("theaterList", theaterService.findAll());
        model.addAttribute("schedule", new ScreeningScheduleSaveRequest());
        model.addAttribute("today", LocalDateTime.now());
        return "screeningSchedule/saveForm";
    }

    @PostMapping("/save")
    public String save(@Validated @ModelAttribute("schedule") ScreeningScheduleSaveRequest form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/screeningSchedule/save?empty=true";
        }

        if (screeningScheduleService.isScreeningScheduleDuplicated(form)) {
            return "redirect:/screeningSchedule/save?duplicate=true";
        }

        screeningScheduleService.save(form);

        return "redirect:/";
    }
}

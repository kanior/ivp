package kanior.ivp.screeningschedule.ui;

import kanior.ivp.movie.query.MovieDao;
import kanior.ivp.screeningschedule.command.application.SaveScreeningScheduleRequest;
import kanior.ivp.screeningschedule.ui.dto.SaveScreeningScheduleForm;
import kanior.ivp.screeningschedule.command.application.SaveScreeningScheduleService;
import kanior.ivp.screeningschedule.ui.dto.MovieListResponse;
import kanior.ivp.screeningschedule.ui.dto.TheaterListResponse;
import kanior.ivp.theater.query.TheaterDao;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/screeningSchedule")
public class ScreeningScheduleController {

    private final MovieDao movieDao;
    private final TheaterDao theaterDao;
    private final SaveScreeningScheduleService saveScreeningScheduleService;

    @GetMapping("/save")
    public String saveForm(Model model){
        model.addAttribute("movieList", getAllMovieList());
        model.addAttribute("theaterList", getAllTheaterList());
        model.addAttribute("schedule", new SaveScreeningScheduleForm());
        model.addAttribute("today", LocalDateTime.now());
        return "screeningSchedule/saveForm";
    }

    @PostMapping("/save")
    public String save(@Validated @ModelAttribute("schedule") SaveScreeningScheduleForm form,
                       BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "redirect:/screeningSchedule/save?empty=true";
        }

        if (saveScreeningScheduleService.isScreeningScheduleExisted(form.getScreenId(), form.getMovieId(), getScreeningDate(form))) {
            return "redirect:/screeningSchedule/save?duplicate=true";
        }

        saveScreeningScheduleService.save(
                new SaveScreeningScheduleRequest(form.getRemainingSeat(), getScreeningDate(form), form.getMovieId(), form.getScreenId()));
        return "redirect:/";
    }

    private LocalDateTime getScreeningDate(SaveScreeningScheduleForm form) {
        return LocalDateTime.parse(form.getScreeningDate() + form.getScreeningHour() + form.getScreeningMin(),
                DateTimeFormatter.ofPattern("yyyy-MM-ddHHmm"));
    }

    private List<TheaterListResponse> getAllTheaterList() {
        return theaterDao.findAll()
                .stream().map(TheaterListResponse::new)
                .collect(Collectors.toList());
    }

    private List<MovieListResponse> getAllMovieList() {
        return movieDao.findAll()
                .stream().map(MovieListResponse::new)
                .collect(Collectors.toList());
    }
}

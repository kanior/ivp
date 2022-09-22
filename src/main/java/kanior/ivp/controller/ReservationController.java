package kanior.ivp.controller;

import kanior.ivp.dto.LoginUserInfo;
import kanior.ivp.dto.MovieListResponse;
import kanior.ivp.dto.ReservationSaveRequest;
import kanior.ivp.dto.TheaterListResponse;
import kanior.ivp.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final MovieService movieService;
    private final TheaterService theaterService;
    private final ReservationService reservationService;

    @GetMapping("/save")
    public String saveForm(Model model) {
        LocalDateTime now = LocalDateTime.now();
        List<MovieListResponse> movieList = movieService.findAllJoinScreeningSchedule(now);
        List<TheaterListResponse> theaterList = theaterService.findAllJoinScreeningSchedule(now);

        model.addAttribute("movieList", movieList);
        model.addAttribute("theaterList", theaterList);
        model.addAttribute("today", now);
        model.addAttribute("reservation", new ReservationSaveRequest());
        return "reservation/saveForm";
    }

    @PostMapping("/save")
    public String save(@Validated @ModelAttribute("reservation") ReservationSaveRequest form, BindingResult bindingResult, Model model, HttpServletRequest request) {
        LoginUserInfo loginUser = (LoginUserInfo) request.getSession().getAttribute(SessionConst.LOGIN_USER);
        if (bindingResult.hasErrors()) {
            return "redirect:/reservation/save?empty=true";
        }

        if (reservationService.save(form) == null) {
            return "redirect:/reservation/save?quantityError=true";
        }

        return "redirect:/?reservation=true";
    }
}

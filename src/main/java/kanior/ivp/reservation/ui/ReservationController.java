package kanior.ivp.reservation.ui;

import kanior.ivp.SessionConst;
import kanior.ivp.reservation.command.application.*;
import kanior.ivp.reservation.query.MyReservationDataDao;
import kanior.ivp.reservation.ui.dto.MyReservationResponse;
import kanior.ivp.reservation.ui.dto.ReservableMovieResponse;
import kanior.ivp.reservation.ui.dto.ReservableTheaterResponse;
import kanior.ivp.reservation.ui.dto.SaveReservationForm;
import kanior.ivp.screeningschedule.query.ScreeningScheduleDao;
import kanior.ivp.user.ui.dto.LoginUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/reservation")
public class ReservationController {

    private final SaveReservationService saveReservationService;
    private final SaveReservationFacade saveReservationFacade;
    private final CancelReservationService cancelReservationService;
    private final ScreeningScheduleDao screeningScheduleDao;
    private final MyReservationDataDao myReservationDataDao;

    @GetMapping("/my/list")
    public String myList(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) LoginUserInfo loginUser,
                         Model model) {

        model.addAttribute("reservationList", getReservationList(loginUser));
        return "reservation/myList";
    }

    @GetMapping("/save")
    public String saveForm(Model model) {
        LocalDateTime now = LocalDateTime.now();

        model.addAttribute("movieList", getReservableMovieList(now));
        model.addAttribute("theaterList", getReservableTheaterList(now));
        model.addAttribute("today", now);
        model.addAttribute("reservation", new SaveReservationForm());
        return "reservation/saveForm";
    }

    @PostMapping("/save")
    public String save(@Validated @ModelAttribute("reservation") SaveReservationForm form,
                       BindingResult bindingResult, Model model,
                       @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) LoginUserInfo loginUser) throws InterruptedException {

        if (bindingResult.hasErrors()) {
            return "redirect:/reservation/save?empty=true";
        }

        if (!saveReservationService.isRemainingSeatEnough(form.getScreeningScheduleId(), form.getReserveCount())) {
            return "redirect:/reservation/save?quantityError=true";
        }

        saveReservationFacade.save(new SaveReservationRequest(loginUser.getLoginId(), form.getScreeningScheduleId(), form.getReserveCount()));
        return "redirect:/?reservation=true";
    }

    @RequestMapping("/cancel/{id}")
    public String cancel(@PathVariable Long id,
                         @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) LoginUserInfo loginUser) {

        cancelReservationService.cancel(new CancelReservationRequest(id, loginUser.getLoginId()));
        return "redirect:/reservation/my/list?cancelReservation=true";
    }

    private List<MyReservationResponse> getReservationList(LoginUserInfo loginUser) {
        return myReservationDataDao.findAllByUserLoginId(loginUser.getLoginId())
                .stream().map(MyReservationResponse::new)
                .collect(Collectors.toList());
    }

    private List<ReservableTheaterResponse> getReservableTheaterList(LocalDateTime now) {
        return screeningScheduleDao.findAllTheatersByScreeningDateAfterNow(now)
                .stream().map(ReservableTheaterResponse::new)
                .collect(Collectors.toList());
    }

    private List<ReservableMovieResponse> getReservableMovieList(LocalDateTime now) {
        return screeningScheduleDao.findAllMoviesByScreeningDateAfterNow(now)
                .stream().map(ReservableMovieResponse::new)
                .collect(Collectors.toList());
    }
}

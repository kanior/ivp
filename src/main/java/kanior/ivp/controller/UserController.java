package kanior.ivp.controller;

import kanior.ivp.dto.*;
import kanior.ivp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/save")
    public String saveForm(Model model) {
        model.addAttribute("user", new UserSaveRequest());
        return "user/saveForm";
    }

    @PostMapping("/save")
    public String save(@Validated @ModelAttribute("user") UserSaveRequest form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/saveForm";
        }

        //아이디 중복 검사
        if (userService.isLoginIdDuplicated(form.getLoginId())) {
            bindingResult.rejectValue("loginId", "NotDuplicated");
            return "user/saveForm";
        }

        //패스워드 검사
        if (!form.getPassword().equals(form.getPasswordCheck())) {
            bindingResult.rejectValue("passwordCheck", "Equal");
            return "user/saveForm";
        }

        userService.save(form);

        return "redirect:/?newUser=true";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("login", new UserLoginRequest());
        return "user/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("login") UserLoginRequest form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request,
                        Model model) {
        if (bindingResult.hasErrors()) {
            return "user/loginForm";
        }

        //회원 여부 검사
        LoginUserInfo loginUserInfo = userService.login(form.getLoginId(), form.getPassword());
        if (loginUserInfo == null) {
            bindingResult.reject("ExistedUserCheck");
            return "user/loginForm";
        }

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, loginUserInfo);

        return "redirect:" + redirectURL;
    }

    @RequestMapping("/logout")
    public String logout(@RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:" + redirectURL;
    }

    @GetMapping("/info")
    public String readInfo(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
            return "redirect:/";
        }

        //회원 정보 조회하여 model에 등록
        String loginId = ((LoginUserInfo) session.getAttribute(SessionConst.LOGIN_USER)).getLoginId();
        UserInfoResponse userInfo = userService.findInfoByLoginId(loginId);
        model.addAttribute("userInfo", userInfo);

        return "user/info";
    }

    @GetMapping("/modifyPassword")
    public String modifyPasswordForm(HttpServletRequest request, Model model) {
        model.addAttribute("modifyPassword", new UserPasswordModifyRequest());
        return "user/modifyPasswordForm";
    }

    @PostMapping("/modifyPassword")
    public String modifyPassword(@Validated @ModelAttribute("modifyPassword") UserPasswordModifyRequest form, BindingResult bindingResult, HttpServletRequest request, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/modifyPasswordForm";
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
            return "redirect:/";
        }

        //현재 비밀번호를 맞게 입력했는지 확인
        String loginId = ((LoginUserInfo) session.getAttribute(SessionConst.LOGIN_USER)).getLoginId();
        LoginUserInfo loginUserInfo = userService.login(loginId, form.getOldPassword());
        if (loginUserInfo == null) {
            bindingResult.rejectValue("oldPassword", "Valid");
            return "user/modifyPasswordForm";
        }

        //새 패스워드 검사
        if (!form.getNewPassword().equals(form.getNewPasswordCheck())) {
            bindingResult.rejectValue("newPasswordCheck", "Equal");
            return "user/modifyPasswordForm";
        }

        //기존 패스워드와 새 패스워드가 같은지 검사
        if (form.getOldPassword().equals(form.getNewPassword())) {
            bindingResult.rejectValue("newPassword", "NotSame");
            return "user/modifyPasswordForm";
        }

        userService.modifyPassword(loginId, form.getNewPassword());

        return "redirect:/user/info?modifyPassword=true";
    }

    @GetMapping("/delete")
    public String deleteForm() {
        return "user/deleteForm";
    }

    @PostMapping("/delete")
    public String delete(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
            return "redirect:/";
        }

        String loginId = ((LoginUserInfo) session.getAttribute(SessionConst.LOGIN_USER)).getLoginId();
        session.invalidate();

        userService.delete(loginId);

        return "redirect:/?userDelete=true";
    }

    @GetMapping("/reservationList")
    public String reservationList(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null) {
            return "redirect:/";
        }

        String loginId = ((LoginUserInfo) session.getAttribute(SessionConst.LOGIN_USER)).getLoginId();
        List<ReservationListResponse> reservationList = userService.findAllReservationByLoginId(loginId);
        model.addAttribute("reservationList", reservationList);
        return "user/reservationList";
    }

}

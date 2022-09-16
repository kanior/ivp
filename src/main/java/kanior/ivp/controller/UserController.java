package kanior.ivp.controller;

import kanior.ivp.dto.LoginUserInfo;
import kanior.ivp.dto.UserLoginRequest;
import kanior.ivp.dto.UserSaveRequest;
import kanior.ivp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
        if (session == null) {
            return "redirect:/";
        }

        //회원 정보 조회하여 model에 등록

        return "user/info";
    }
}

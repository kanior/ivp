package kanior.ivp.user.ui;

import kanior.ivp.SessionConst;
import kanior.ivp.user.command.application.*;
import kanior.ivp.user.command.domain.User;
import kanior.ivp.user.ui.dto.*;
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
    private final SaveUserService saveUserService;
    private final ModifyPasswordService modifyPasswordService;

    @GetMapping("/save")
    public String saveForm(Model model) {
        model.addAttribute("user", new SaveUserForm());
        return "user/saveForm";
    }

    @PostMapping("/save")
    public String save(@Validated @ModelAttribute("user") SaveUserForm form,
                       BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors() ||
                isLoginIdDuplicated(form.getLoginId(), bindingResult) ||
                isPasswordNotSame(form.getPassword(), form.getPasswordCheck(), bindingResult)) {
            return "user/saveForm";
        }

        saveUserService.save(new SaveUserRequest(form.getLoginId(), form.getPassword(), form.getName(), form.getBirthDate()));
        return "redirect:/?newUser=true";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("login", new LoginUserForm());
        return "user/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("login") LoginUserForm form,
                        BindingResult bindingResult, Model model,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {

        if (bindingResult.hasErrors() ||
                isLoginIdOrPasswordWrong(form.getLoginId(), form.getPassword(), bindingResult)) {
            return "user/loginForm";
        }
        setLoginSession(request.getSession(), userService.findByLoginId(form.getLoginId()));
        return "redirect:" + redirectURL;
    }

    @RequestMapping("/logout")
    public String logout(@RequestParam(defaultValue = "/") String redirectURL,
                         HttpServletRequest request) {

        invalidateLoginSession(request.getSession(false));
        return "redirect:" + redirectURL;
    }

    @GetMapping("/info")
    public String readInfo(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) LoginUserInfo loginUser,
                           Model model) {

        model.addAttribute("userInfo", new UserInfoResponse(userService.findByLoginId(loginUser.getLoginId())));
        return "user/info";
    }

    @GetMapping("/modifyPassword")
    public String modifyPasswordForm(Model model) {
        model.addAttribute("modifyPassword", new PasswordModifyForm());
        return "user/modifyPasswordForm";
    }

    @PostMapping("/modifyPassword")
    public String modifyPassword(@Validated @ModelAttribute("modifyPassword") PasswordModifyForm form,
                                 BindingResult bindingResult, Model model,
                                 @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) LoginUserInfo loginUser) {

        if (bindingResult.hasErrors()
                || isOldPasswordWrong(loginUser.getLoginId(), form.getOldPassword(), bindingResult)
                || isNewPasswordNotSame(form.getNewPassword(), form.getNewPasswordCheck(), bindingResult)
                || isOldPasswordAndNewPasswordSame(form.getOldPassword(), form.getNewPassword(), bindingResult)) {
            return "user/modifyPasswordForm";
        }

        modifyPasswordService.modifyPassword(new ModifyPasswordRequest(loginUser.getLoginId(), form.getOldPassword(), form.getNewPassword()));
        return "redirect:/user/info?modifyPassword=true";
    }

    @GetMapping("/delete")
    public String deleteForm() {
        return "user/deleteForm";
    }

    @PostMapping("/delete")
    public String delete(HttpServletRequest request,
                         @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) LoginUserInfo loginUser) {

        request.getSession(false).invalidate();
        userService.delete(loginUser.getLoginId());
        return "redirect:/?userDelete=true";
    }

    private boolean isLoginIdDuplicated(String loginId, BindingResult bindingResult) {
        if (saveUserService.isLoginIdExisted(loginId)) {
            bindingResult.rejectValue("loginId", "NotDuplicated");
            return true;
        }
        return false;
    }

    private boolean isPasswordNotSame(String password, String passwordCheck, BindingResult bindingResult) {
        if (!password.equals(passwordCheck)) {
            bindingResult.rejectValue("passwordCheck", "Equal");
            return true;
        }
        return false;
    }

    private boolean isLoginIdOrPasswordWrong(String loginId, String password, BindingResult bindingResult) {
        if (!userService.isLoginIdAndPasswordValid(loginId, password)) {
            bindingResult.reject("ExistedUserCheck");
            return true;
        }
        return false;
    }

    private boolean isOldPasswordWrong(String loginId, String oldPassword, BindingResult bindingResult) {
        if (!modifyPasswordService.isOldPasswordValid(loginId, oldPassword)) {
            bindingResult.rejectValue("oldPassword", "Valid");
            return true;
        }
        return false;
    }

    private boolean isNewPasswordNotSame(String newPassword, String newPasswordCheck, BindingResult bindingResult) {
        if (!newPassword.equals(newPasswordCheck)) {
            bindingResult.rejectValue("newPasswordCheck", "Equal");
            return true;
        }
        return false;
    }

    private boolean isOldPasswordAndNewPasswordSame(String oldPassword, String newPassword, BindingResult bindingResult) {
        if (oldPassword.equals(newPassword)) {
            bindingResult.rejectValue("newPassword", "NotSame");
            return true;
        }
        return false;
    }

    private void setLoginSession(HttpSession session, User user) {
        session.setAttribute(SessionConst.LOGIN_USER, new LoginUserInfo(user));
    }

    private void invalidateLoginSession(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
    }

}

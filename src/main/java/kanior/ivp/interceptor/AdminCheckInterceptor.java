package kanior.ivp.interceptor;

import kanior.ivp.SessionConst;
import kanior.ivp.user.ui.dto.LoginUserInfo;
import kanior.ivp.user.command.domain.UserRole;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

public class AdminCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_USER) == null || ((LoginUserInfo)session.getAttribute(SessionConst.LOGIN_USER)).getRole() != UserRole.ADMIN) {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('관리자만 접근할 수 있습니다!'); history.go(-1);</script>");
            out.flush();
            return false;
        }
        return true;
    }
}

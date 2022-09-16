package kanior.ivp.interceptor;

import kanior.ivp.controller.SessionConst;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

public class LogoutCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();

        if (session != null && session.getAttribute(SessionConst.LOGIN_USER) != null) {
            response.sendRedirect("/logout?redirectURL=" + requestURI);
            return false;
        }

        return true;
    }
}

package kanior.ivp;

import kanior.ivp.interceptor.AdminCheckInterceptor;
import kanior.ivp.interceptor.LoginCheckInterceptor;
import kanior.ivp.interceptor.LogoutCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/",
                        "/user/save", "/user/login", "/user/logout",
                        "/movie/**",
                        "/theater/**",
                        "/screeningSchedule/**",
                        "/css/**", "/images/**", "/*.ico", "/error");

        registry.addInterceptor(new AdminCheckInterceptor())
                .order(2)
                .addPathPatterns("/screeningSchedule/**");

        registry.addInterceptor(new LogoutCheckInterceptor())
                .order(3)
                .addPathPatterns("/user/save", "/user/login");
    }
}

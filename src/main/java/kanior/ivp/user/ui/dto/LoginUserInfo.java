package kanior.ivp.user.ui.dto;

import kanior.ivp.user.command.domain.User;
import kanior.ivp.user.command.domain.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginUserInfo {

    private String loginId;

    private String name;

    private String birthDate;

    private UserRole role;

    public LoginUserInfo(User entity) {
        this.loginId = entity.getLoginId();
        this.name = entity.getName();
        this.birthDate = entity.getBirthDate();
        this.role = entity.getRole();
    }
}

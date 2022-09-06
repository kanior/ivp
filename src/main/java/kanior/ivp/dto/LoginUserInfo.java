package kanior.ivp.dto;

import kanior.ivp.entity.User;
import kanior.ivp.entity.UserRole;
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

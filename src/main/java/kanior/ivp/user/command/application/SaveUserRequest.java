package kanior.ivp.user.command.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SaveUserRequest {

    private String loginId;

    private String password;

    private String name;

    private String birthDate;
}

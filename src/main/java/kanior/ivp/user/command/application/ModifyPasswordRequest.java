package kanior.ivp.user.command.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyPasswordRequest {

    private String loginId;

    private String oldPassword;

    private String newPassword;
}

package kanior.ivp.user.ui.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class PasswordModifyForm {

    @NotBlank
    @Pattern(regexp = "[\\w\\W]{8,16}")
    private String oldPassword;

    @NotBlank
    @Pattern(regexp = "[\\w\\W]{8,16}")
    private String newPassword;

    @NotBlank
    private String newPasswordCheck;
}

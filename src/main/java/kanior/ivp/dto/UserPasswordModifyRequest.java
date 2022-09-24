package kanior.ivp.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserPasswordModifyRequest {

    @NotBlank
    @Pattern(regexp = "[\\w\\W]{8,16}")
    private String oldPassword;

    @NotBlank
    @Pattern(regexp = "[\\w\\W]{8,16}")
    private String newPassword;

    @NotBlank
    private String newPasswordCheck;
}

package kanior.ivp.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserLoginRequest {

    @NotBlank
    @Pattern(regexp = "[a-z\\d_-]{5,20}")
    private String loginId;

    @NotBlank
    @Pattern(regexp = "[\\w\\W]{8,16}")
    private String password;
}

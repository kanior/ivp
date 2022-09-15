package kanior.ivp.dto;

import kanior.ivp.entity.User;
import kanior.ivp.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserSaveRequest {

    @NotBlank
    @Pattern(regexp = "[a-z\\d_-]{5,20}")
    private String loginId;

    @NotBlank
    @Pattern(regexp = "[\\w\\W]{8,16}")
    private String password;

    @NotBlank
    private String passwordCheck;

    @NotBlank
    @Pattern(regexp = "[가-힣a-zA-Z]{1,20}")
    private String name;

    @NotBlank
    @Pattern(regexp = "(\\d{2})((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))")
    private String birthDate;

    public User toEntity() {
        return User.builder()
                .loginId(this.loginId)
                .password(this.password)
                .name(this.name)
                .birthDate(this.birthDate)
                .role(UserRole.USER)
                .build();
    }
}

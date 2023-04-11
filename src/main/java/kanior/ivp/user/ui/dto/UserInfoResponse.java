package kanior.ivp.user.ui.dto;

import kanior.ivp.user.command.domain.User;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class UserInfoResponse {

    private String loginId;

    private String name;

    private String createdDate;

    private String birthDate;

    public UserInfoResponse(User user) {
        this.loginId = user.getLoginId();
        this.name = user.getName();
        this.createdDate = user.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        this.birthDate = user.getBirthDate();
    }
}

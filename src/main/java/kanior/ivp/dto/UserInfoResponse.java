package kanior.ivp.dto;

import kanior.ivp.entity.User;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
public class UserInfoResponse {

    private String loginId;

    private String name;

    private String createdDate;

    private String birthDate;

    public UserInfoResponse(User entity) {
        this.loginId = entity.getLoginId();
        this.name = entity.getName();
        this.createdDate = entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일"));
        this.birthDate = entity.getBirthDate();
    }
}

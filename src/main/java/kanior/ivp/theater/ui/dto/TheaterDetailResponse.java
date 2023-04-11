package kanior.ivp.theater.ui.dto;

import kanior.ivp.theater.command.domain.Theater;
import lombok.Getter;

@Getter
public class TheaterDetailResponse {

    private String name;

    private String address;

    private String tel;

    public TheaterDetailResponse(Theater entity) {
        this.name = entity.getName();
        this.address = entity.getAddress();
        this.tel = entity.getTel();
    }
}

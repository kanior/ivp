package kanior.ivp.dto;

import kanior.ivp.entity.Theater;
import lombok.Getter;

@Getter
public class TheaterInfoResponse {

    private String name;

    private String address;

    private String tel;

    public TheaterInfoResponse(Theater entity) {
        this.name = entity.getName();
        this.address = entity.getAddress();
        this.tel = entity.getTel();
    }
}

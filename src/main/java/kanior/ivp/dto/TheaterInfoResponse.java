package kanior.ivp.dto;

import kanior.ivp.entity.Theater;
import lombok.Getter;

@Getter
public class TheaterInfoResponse {

    private String name;

    private String address;

    private String tel;

    private Integer totalScreen;

    private Integer totalSeat;

    public TheaterInfoResponse(Theater theater, Integer totalScreen, Integer totalSeat) {
        this.name = theater.getName();
        this.address = theater.getAddress();
        this.tel = theater.getTel();
        this.totalScreen = totalScreen;
        this.totalSeat = totalSeat;
    }
}

package kanior.ivp.dto;

import kanior.ivp.entity.Theater;
import lombok.Getter;

@Getter
public class TheaterListResponse {

    private Long id;

    private String name;

    public TheaterListResponse(Theater theater) {
        this.id = theater.getId();
        this.name = theater.getName();
    }
}

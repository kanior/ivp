package kanior.ivp.screeningschedule.ui.dto;

import kanior.ivp.theater.command.domain.Theater;
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

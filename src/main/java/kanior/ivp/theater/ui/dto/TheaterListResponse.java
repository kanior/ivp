package kanior.ivp.theater.ui.dto;

import kanior.ivp.theater.command.domain.Theater;
import lombok.Getter;

@Getter
public class TheaterListResponse {

    private Long id;

    private String name;

    public TheaterListResponse(Theater entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}

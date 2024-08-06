package management.project.movie.model.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ActorResponse {

    private String name;

    public ActorResponse(String name) {
        this.name = name;
    }
}

package management.project.movie.model.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class MovieActors {

    private String movieName;
    private List<ActorResponse> actors;
}

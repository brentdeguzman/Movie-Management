package management.project.movie.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class MovieActors {

    private String movieName;
    private List<ActorResponse> actors;
}

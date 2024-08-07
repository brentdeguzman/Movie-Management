package management.project.movie.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ActorMovieList {
    private String actorName;
    private List<MovieResponse> movies;
}

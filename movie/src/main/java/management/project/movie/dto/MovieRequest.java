package management.project.movie.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import management.project.movie.model.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {

    private String name;
    private int releaseYear;
    private Director director;
    private List<Actor> actors;
    private List<Genre> genres;
    private Rating rating;

    public MovieRequest(Movie movie) {
    }
}

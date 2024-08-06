package management.project.movie.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import management.project.movie.model.Actor;
import management.project.movie.model.Director;
import management.project.movie.model.Genre;
import management.project.movie.model.Movie;

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
    private String roleCharacterName;

    public MovieRequest(Movie movie) {

    }
}

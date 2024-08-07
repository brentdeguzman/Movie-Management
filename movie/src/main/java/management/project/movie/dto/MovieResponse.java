package management.project.movie.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MovieResponse {

    private String name;

    public MovieResponse(String name) {
        this.name = name;
    }
}

package management.project.movie.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class MovieUpdate {
    private String name;
    private int releaseYear;
}

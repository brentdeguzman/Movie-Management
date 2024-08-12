package management.project.movie.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ReviewsRequest {
    private String comment;
    private Long userId;
    private Long date;
    private Long ratingId;

}

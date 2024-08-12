package management.project.movie.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "reviews")
@RequiredArgsConstructor
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "date")
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "movie_id")
    private Rating rating;

    public void setUserId(Long userId) {

    }
}

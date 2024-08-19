package management.project.movie.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int releaseYear;

    @OneToOne
    @JoinColumn(name = "director_id")
    private Director director;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieCasting> movieCastings;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rating_id")
    private Rating rating;

    @ManyToMany
    private List<Actor> actors;

    public void setActors(List<Actor> savedActors) {
    }

    public String getGenresAsString() {
        return genres.stream()
                .map(Genre::getName)
                .collect(Collectors.joining(", "));
    }//processes the elements of the stream and combines them into a single result

    public String getActorsAsString(List<Actor> savedActors) {
        return savedActors.stream()
                .map(Actor::getName)
                .collect(Collectors.joining(", "));
    }

    @OneToOne
    @JoinColumn(name = "file_reference_id")
    private FileReference fileReference;
}

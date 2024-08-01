package management.project.movie.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import management.project.movie.embeddable.MovieCastingId;

import javax.persistence.*;

@Entity
@Table(name = "movie_casting")
@IdClass(MovieCastingId.class)
public class MovieCasting {

    @Id
    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    private Movie movie;

    @Id
    @ManyToOne
    @JoinColumn(name = "actor_id")
    private Actor actor;

    @Column(name = "role")
    private String role;

    // Getters and Setters
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}




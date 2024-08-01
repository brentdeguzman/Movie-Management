package management.project.movie.embeddable;

import java.io.Serializable;
import java.util.Objects;

public class MovieCastingId implements Serializable {
    private Long movie;
    private Long actor;

    public MovieCastingId() {}

    public MovieCastingId(Long movie, Long actor) {
        this.movie = movie;
        this.actor = actor;
    }

    public Long getMovie() {
        return movie;
    }

    public void setMovie(Long movie) {
        this.movie = movie;
    }

    public Long getActor() {
        return actor;
    }

    public void setActor(Long actor) {
        this.actor = actor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieCastingId that = (MovieCastingId) o;
        return Objects.equals(movie, that.movie) &&
                Objects.equals(actor, that.actor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, actor);
    }
}


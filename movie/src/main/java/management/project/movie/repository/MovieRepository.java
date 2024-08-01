package management.project.movie.repository;

import management.project.movie.model.Movie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
//    @EntityGraph(attributePaths = {"movieCastings"})
//    List<Movie> findAllWithMovieCastings();
}

package management.project.movie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import management.project.movie.model.MovieCasting;

@Repository
public interface MovieCastingRepository extends JpaRepository<MovieCasting, Long> {
}


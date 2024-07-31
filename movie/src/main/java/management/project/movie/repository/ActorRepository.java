package management.project.movie.repository;

import management.project.movie.model.Actor;
import management.project.movie.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    Optional<Actor> findByName(String name);
}

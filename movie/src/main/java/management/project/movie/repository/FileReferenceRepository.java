package management.project.movie.repository;

import management.project.movie.model.FileReference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileReferenceRepository extends JpaRepository<FileReference, Long> {
    // Custom queries can be added here if needed
}


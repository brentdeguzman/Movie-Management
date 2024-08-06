package management.project.movie.controller;

import management.project.movie.model.Director;
import management.project.movie.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies/directors")
public class DirectorController {

    @Autowired
    private DirectorService directorService;

    @GetMapping
    public List<Director> getAllDirectors() {
        return directorService.getAllDirectors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Director> getDirectorById(@PathVariable("id") Long id) {
        Optional<Director> director = directorService.getDirectorById(id);
        if (director.isPresent()) {
            return ResponseEntity.ok(director.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Director> createDirector(@RequestBody Director director) {
        Director savedDirector = directorService.createDirector(director);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDirector);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Director> updateDirector(@PathVariable("id") Long id, @RequestBody Director director) {
        if (!directorService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Director updatedDirector = directorService.updateDirector(id, director);
        return ResponseEntity.ok(updatedDirector);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirector(@PathVariable("id") Long id) {
        if (!directorService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        directorService.deleteDirector(id);
        return ResponseEntity.noContent().build();
    }
}


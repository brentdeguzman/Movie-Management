package management.project.movie.controller;

import management.project.movie.model.Actor;
import management.project.movie.model.response.ActorMovieList;
import management.project.movie.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies/actors")
public class ActorController {

    @Autowired
    private ActorService actorService;

    @GetMapping
    public List<Actor> getAllActors() {
        return actorService.getAllActors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Actor> getActorById(@PathVariable("id") Long id) {
        Optional<Actor> actor = actorService.getActorById(id);
        if (actor.isPresent()) {
            return ResponseEntity.ok(actor.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Actor> createActor(@RequestBody Actor actor) {
        Actor savedActor = actorService.createActor(actor);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedActor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Actor> updateActor(@PathVariable("id") Long id, @RequestBody Actor actor) {
        if (!actorService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        Actor updatedActor = actorService.updateActor(id, actor);
        return ResponseEntity.ok(updatedActor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActor(@PathVariable("id") Long id) {
        if (!actorService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        actorService.deleteActor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{actorId}/movies")
    public ActorMovieList getMovieActors(@PathVariable("actorId") Long actorId) {
        return actorService.getActorsByMovie(actorId);
    }
}


package management.project.movie.controller;

import management.project.movie.dto.MovieUpdate;
import management.project.movie.model.Movie;
import management.project.movie.dto.MovieActors;
import management.project.movie.dto.MovieRequest;
import management.project.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{movieId}/actors")
    public MovieActors getMovieActors(@PathVariable("movieId") Long movieId) {
        return movieService.getActorsByMovie(movieId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") Long id) {
        Optional<Movie> movie = movieService.getMovieById(id);
        if (movie.isPresent()) {
            return ResponseEntity.ok(movie.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody MovieRequest movieRequest) {
        Movie movie = movieService.createMovie(movieRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable("id") Long id, @RequestBody MovieUpdate movieUpdate) {
        Optional<Movie> movieOptional = movieService.getMovieById(id);

        if (!movieOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Movie movie = movieOptional.get();
        movie.setName(movieUpdate.getName());
        movie.setReleaseYear(movieUpdate.getReleaseYear());

        Movie updatedMovie = movieService.save(movie);
        return ResponseEntity.ok(updatedMovie);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("id") Long id) {
        if (!movieService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}

package management.project.movie.controller;

import management.project.movie.model.*;
import management.project.movie.repository.*;
import management.project.movie.service.MovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private DirectorRepository directorRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MovieCastingRepository movieCastingRepository;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable("id") Long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isPresent()) {
            return ResponseEntity.ok(movie.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Movie> createMovie(@RequestBody MovieRequest movieRequest) {
        // Handle the director
        Director director = movieRequest.getDirector();
        Director savedDirector = directorRepository.findByName(director.getName())
                .orElseGet(() -> directorRepository.save(director));

        // Handle actors
        List<Actor> actors = movieRequest.getActors();
        List<Actor> savedActors = new ArrayList<>();
        for (Actor actor : actors) {
            Actor savedActor = actorRepository.findByName(actor.getName())
                    .orElseGet(() -> actorRepository.save(actor));
            savedActors.add(savedActor);
        }

        // Create and save the movie
        Movie movie = new Movie();
        movie.setName(movieRequest.getName());
        movie.setReleaseYear(movieRequest.getReleaseYear());
        movie.setDirector(savedDirector);

        Movie savedMovie = movieRepository.save(movie);
        List <MovieCasting> movieCastings = new ArrayList<>();
        // Save movie castings
        for (Actor actor : savedActors) {
            MovieCasting movieCasting = new MovieCasting();
            movieCasting.setMovie(savedMovie);
            movieCasting.setActor(actor);
            movieCasting.setRole(actor.getRole());
            movieCastingRepository.save(movieCasting);
            movieCastings.add(movieCasting);
        }
        savedMovie.setMovieCastings(movieCastings);
        movieRepository.save(savedMovie);
        // Save genres
        List<Genre> genres = movieRequest.getGenres();
        List<Genre> savedGenres = new ArrayList<>();
        for (Genre genre : genres) {
            Genre savedGenre = genreRepository.findByName(genre.getName())
                    .orElseGet(() -> genreRepository.save(genre));
            savedGenres.add(savedGenre);
        }

        // Set genres for movie (if you have a method for this)
        savedMovie.setGenres(savedGenres);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieRepository.save(savedMovie));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable("id") Long id, @RequestBody Movie movie) {
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        movie.setId(id);
        Movie updatedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable("id") Long id) {
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        movieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

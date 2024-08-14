package management.project.movie.controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import management.project.movie.dto.MovieActors;
import management.project.movie.dto.MovieRequest;
import management.project.movie.dto.MovieUpdate;
import management.project.movie.model.*;
//import management.project.movie.service.CSVService;
import management.project.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

//    @Autowired
//    private CSVService csvService;

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

        Optional<Movie> existingMovie = movieService.getMovieById(id);

        if (!existingMovie.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Movie movie = existingMovie.get();
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

    @PostMapping("/insert-file")
    public ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] line;

            boolean isHeader = true;
            while ((line = reader.readNext()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String name = line[0];
                int releaseYear = Integer.parseInt(line[1]);
                String directorName = line[2];
                String genresString = line[3];
                String actorsString = line[4];
                int score = Integer.parseInt(line[5]);
                Rating rating = new Rating(score);

                Director director = movieService.findOrCreateDirector(directorName);
                List<Genre> genres = movieService.findOrCreateGenres(genresString);
                List<Actor> actors = movieService.findOrCreateActors(actorsString);

                movieService.saveMovies(name, releaseYear, director, genres, actors, rating);
            }
            return ResponseEntity.ok("File processed successfully!");
        } catch (IOException | CsvValidationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid number format in CSV file: " + e.getMessage());
        }
    }
}


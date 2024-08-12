package management.project.movie.controller;

import management.project.movie.model.Rating;
import management.project.movie.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/movies/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        Rating savedRating = ratingService.save(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRating);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<Rating> getRatingByMovieId(@PathVariable("movieId") Long movieId) {
        Optional<Rating> rating = ratingService.getRatingByMovieId(movieId);
        return rating.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<Void> deleteRating(@PathVariable("movieId") Long movieId) {
        if (ratingService.getRatingByMovieId(movieId).isPresent()) {
            ratingService.deleteByMovieId(movieId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

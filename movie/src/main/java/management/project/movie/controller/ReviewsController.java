package management.project.movie.controller;

import management.project.movie.dto.ReviewsRequest;
import management.project.movie.model.Reviews;
import management.project.movie.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies/reviews")
public class ReviewsController {

    @Autowired
    private ReviewsService reviewsService;

    @PostMapping("/reviews")
    public ResponseEntity<Reviews> createReview(@RequestBody ReviewsRequest reviewsRequest) {
        Reviews createdReview = reviewsService.createReview(reviewsRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reviews> getReviewById(@PathVariable("id") int id) {
        return reviewsService.getReviewById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Reviews> getAllReviews() {
        return reviewsService.getAllReviews();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reviews> updateReview(@PathVariable("id") int id, @RequestBody Reviews review) {
        Reviews updatedReview = reviewsService.updateReview(id, review);
        if (updatedReview != null) {
            return ResponseEntity.ok(updatedReview);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable("id") int id) {
        reviewsService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}

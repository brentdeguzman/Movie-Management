package management.project.movie.service;

import management.project.movie.dto.ReviewsRequest;
import management.project.movie.model.Rating;
import management.project.movie.model.Reviews;
import management.project.movie.repository.RatingRepository;
import management.project.movie.repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewsService {

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Transactional
    public Reviews createReview(ReviewsRequest reviewRequest) {
        Reviews review = new Reviews();
        review.setComment(reviewRequest.getComment());
        review.setUserId(reviewRequest.getUserId());
        review.setDate(LocalDate.now());

        // Retrieve and set the Rating
        Rating rating = ratingRepository.findById(reviewRequest.getRatingId())
                .orElseThrow(() -> new EntityNotFoundException("Rating not found"));
        review.setRating(rating);

        return reviewsRepository.save(review);
    }



    public Optional<Reviews> getReviewById(int id) {
        return reviewsRepository.findById(id);
    }

    public List<Reviews> getAllReviews() {
        return reviewsRepository.findAll();
    }

    @Transactional
    public Reviews updateReview(int id, Reviews review) {
        if (reviewsRepository.existsById(id)) {
            review.setId(id);
            return reviewsRepository.save(review);
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteReview(int id) {
        if (reviewsRepository.existsById(id)) {
            reviewsRepository.deleteById(id);
        }
    }
}

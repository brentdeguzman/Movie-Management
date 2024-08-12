package management.project.movie.service;

import management.project.movie.model.Rating;
import management.project.movie.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }

    public Optional<Rating> getRatingByMovieId(Long movieId) {
        return ratingRepository.findById(movieId);
    }

    public void deleteByMovieId(Long movieId) {
        ratingRepository.deleteById(movieId);
    }
}

package management.project.movie.service;

import management.project.movie.model.*;
import management.project.movie.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

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

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }

    @Transactional
    public Movie createMovie(MovieRequest movieRequest) {

        Director director = movieRequest.getDirector();
        Director savedDirector = directorRepository.findByName(director.getName())
                .orElseGet(() -> directorRepository.save(director));

        List<Actor> actors = movieRequest.getActors();
        List<Actor> savedActors = new ArrayList<>();
        for (Actor actor : actors) {
            Actor savedActor = actorRepository.findByName(actor.getName())
                    .orElseGet(() -> actorRepository.save(actor));
            savedActors.add(savedActor);
        }

        Movie movie = new Movie();
        movie.setName(movieRequest.getName());
        movie.setReleaseYear(movieRequest.getReleaseYear());
        movie.setDirector(savedDirector);

        Movie savedMovie = movieRepository.save(movie);
        List<MovieCasting> movieCastings = new ArrayList<>();
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

        List<Genre> genres = movieRequest.getGenres();
        List<Genre> savedGenres = new ArrayList<>();
        for (Genre genre : genres) {
            Genre savedGenre = genreRepository.findByName(genre.getName())
                    .orElseGet(() -> genreRepository.save(genre));
            savedGenres.add(savedGenre);
        }

        savedMovie.setGenres(savedGenres);
        return movieRepository.save(savedMovie);
    }

    public boolean existsById(Long id) {
        return movieRepository.existsById(id);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
}

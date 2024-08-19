package management.project.movie.service;

import management.project.movie.dto.ActorResponse;
import management.project.movie.dto.MovieActors;
import management.project.movie.dto.MovieRequest;
import management.project.movie.model.*;
import management.project.movie.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private FileReferenceRepository fileReferenceRepository;

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
        Set<MovieCasting> movieCastings = new HashSet<>();
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

        Rating rating = movieRequest.getRating();
        if (rating != null) {
            Rating savedRating = ratingRepository.save(rating);
            savedMovie.setRating(savedRating);
        }

        savedMovie.setGenres(savedGenres);
        return movieRepository.save(savedMovie);
    }

    public MovieActors getActorsByMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId).get();
        List<Actor> movieActors = movie.getMovieCastings().stream().map(MovieCasting::getActor).collect(Collectors.toList());
        MovieActors movieActorsResponse = new MovieActors();
        movieActorsResponse.setActors(movieActors.stream().map(actor -> new ActorResponse(actor.getName())).collect(Collectors.toList()));
        movieActorsResponse.setMovieName(movie.getName());
        return movieActorsResponse;
    }

    public boolean existsById(Long id) {
        return movieRepository.existsById(id);
    }

    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public void saveMovies(String name, int releaseYear, Director director, List<Genre> genres, List<Actor> actors, Rating rating) {
        Movie movie = new Movie();
        List<Genre> genreList = new ArrayList<>(genres);
        List<Actor> actorList = new ArrayList<>(actors);

        movie.setName(name);
        movie.setReleaseYear(releaseYear);
        movie.setDirector(director);
        movie.setGenres(genreList);
        movie.setActors(actorList);
        movie.setRating(rating);

        movieRepository.save(movie);
    }

    public Director findOrCreateDirector(String directorName) {
        return directorRepository.findByName(directorName)
                .orElseGet(() -> directorRepository.save(new Director(directorName)));
    }

    public List<Genre> findOrCreateGenres(String genresString) {
        return Arrays.stream(genresString.split(";"))
                .map(this::findOrCreateGenre)
                .collect(Collectors.toList());
    }

    private Genre findOrCreateGenre(String genreName) {
        return genreRepository.findByName(genreName)
                .orElseGet(() -> genreRepository.save(new Genre(genreName)));
    }

    public List<Actor> findOrCreateActors(String actorsString) {
        return Arrays.stream(actorsString.split(";"))
                .map(this::createActorFromString)
                .collect(Collectors.toList());
    }

    private Actor createActorFromString(String actorString) {
        String[] parts = actorString.split(":");
        String actorName = parts[0].trim();
        String role = parts.length > 1 ? parts[1].trim() : null;

        Actor actor = findOrCreateActor(actorName);
        if (role != null) {
            actor.setRole(role);
        }
        return actor;
    }

    private Actor findOrCreateActor(String actorName) {
        return actorRepository.findByName(actorName)
                .orElseGet(() -> actorRepository.save(new Actor(actorName)));
    }

    @Transactional
    public void saveFileReferenceForMovie(Movie movie, String bucketName, String objectName, String fileUrl) {
        FileReference fileReference = new FileReference();
        fileReference.setBucketName(bucketName);
        fileReference.setObjectName(objectName);
        fileReference.setFileUrl(fileUrl);
        fileReference = fileReferenceRepository.save(fileReference);

        movie.setFileReference(fileReference);
        movieRepository.save(movie);
    }

    public Movie findMovieByName(String name) {
        return movieRepository.findByName(name).orElseThrow(() -> new RuntimeException("Movie not found"));
    }
}


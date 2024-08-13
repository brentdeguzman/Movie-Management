package management.project.movie.service;

import management.project.movie.dto.ActorMovieList;
import management.project.movie.dto.MovieResponse;
import management.project.movie.model.Actor;
import management.project.movie.model.Movie;
import management.project.movie.model.MovieCasting;
import management.project.movie.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    public ActorMovieList getActorsByMovie(Long actorId) {
        Actor actor = actorRepository.findById(actorId).get();
        List<Movie> actorMovies = actor.getMovieCastings().stream().map(MovieCasting::getMovie).collect(Collectors.toList());
        ActorMovieList actorMovieListResponse = new ActorMovieList();
        actorMovieListResponse.setMovies(actorMovies.stream().map(movie -> new MovieResponse(movie.getName())).collect(Collectors.toList()));
        actorMovieListResponse.setActorName(actor.getName());
        return actorMovieListResponse;
    }

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    public Optional<Actor> getActorById(Long id) {
        return actorRepository.findById(id);
    }

    public Actor createActor(Actor actor) {
        return actorRepository.save(actor);
    }

    public boolean existsById(Long id) {
        return actorRepository.existsById(id);
    }

    public Actor updateActor(Long id, Actor actor) {
        actor.setId(id);
        return actorRepository.save(actor);
    }

    public void deleteActor(Long id) {
        actorRepository.deleteById(id);
    }
}


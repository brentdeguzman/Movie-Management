//package management.project.movie.model;
//
//import lombok.*;
//import management.project.movie.embeddable.MovieCastingId;
//
//import javax.persistence.*;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class MovieCasting {
//
//    @EmbeddedId
//    private MovieCastingId id;
//
//    @ManyToOne
//    @MapsId("movieId")
//    @JoinColumn(name = "movie_id")
//    private Movie movie;
//
//    @ManyToOne
//    @MapsId("actorId")
//    @JoinColumn(name = "actor_id")
//    private Actor actor;
//
//    private String role;
//}

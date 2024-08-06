package management.project.movie.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "actor")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String role;

    private String roleCharacterName;

    @OneToMany(mappedBy = "actor")
    @JsonIgnore
    private List<MovieCasting> movieCastings;

//helper repo method : from movie, list all cast actors, bypass movie casting
}

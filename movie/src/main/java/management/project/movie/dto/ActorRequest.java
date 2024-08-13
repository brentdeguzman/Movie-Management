package management.project.movie.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ActorRequest {
    private String name;
    private String role;
    private String roleCharacter;

    public ActorRequest(String name, String role) {
        this.name = name;
        this.role = role;
    }
}



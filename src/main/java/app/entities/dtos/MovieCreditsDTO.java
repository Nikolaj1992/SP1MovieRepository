package app.entities.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieCreditsDTO {

    private int id;
    private List<ActorDTO> actors;
    private List<DirectorDTO> directors;

}

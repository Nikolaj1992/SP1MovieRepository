package app.entities.dtos;

import app.entities.Actor;
import app.entities.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActorDTO {

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
//    @JsonProperty("character") // why do we even fetch this?? it's going to cause problem and isn't even on the entity
//    private String character;


    public ActorDTO(Actor actor) {
        this.id = actor.getId();
        this.name = actor.getName();
    }

}

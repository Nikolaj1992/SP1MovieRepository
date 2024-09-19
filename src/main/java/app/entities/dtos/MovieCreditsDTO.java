package app.entities.dtos;

import app.entities.MovieCredits;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieCreditsDTO { //TODO: change this method to function in a way that lets the illustration from 17/09/2024
    @JsonProperty("id") //this is the id of the movie the cast and crew are connected to
    private int id;
    @JsonProperty("cast")
//    @JsonDeserialize(using = CastIdDeserializer.class)
//    private List<Integer> castIds;
    private List<ActorDTO> cast;
    @JsonProperty("crew")
//    @JsonDeserialize(using = CrewIdDeserializer.class)
//    private List<Integer> crewIds;
    private List<DirectorDTO> crew;

    public MovieCreditsDTO(MovieCredits credits) {
        this.id = credits.getId();
        this.cast = credits.getActors() != null ?
                credits.getActors().stream()
                .map(ActorDTO::new)
                .collect(Collectors.toList()) : new ArrayList<>();
        this.crew = credits.getDirectors() != null ?
                credits.getDirectors().stream()
                .map(DirectorDTO::new)
                .collect(Collectors.toList()) : new ArrayList<>();
    }

}

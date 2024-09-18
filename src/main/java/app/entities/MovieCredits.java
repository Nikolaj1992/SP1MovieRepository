package app.entities;

import app.custom_deserializers.CastIdDeserializer;
import app.custom_deserializers.CrewIdDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieCredits { //TODO: change this method to function in a way that lets the illustration from 17/09/2024
    @JsonProperty("id")
    private int id;
    @JsonProperty("cast")
    @JsonDeserialize(using = CastIdDeserializer.class)
    private List<Integer> castIds;
    @JsonProperty("crew")
    @JsonDeserialize(using = CrewIdDeserializer.class)
    private List<Integer> crewIds;
}

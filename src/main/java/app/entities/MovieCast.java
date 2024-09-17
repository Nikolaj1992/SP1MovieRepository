package app.entities;

import app.api.deserializers.CastIdDeserializer;
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
public class MovieCast {
    @JsonProperty("id")
    private int id;
    @JsonProperty("cast")
    @JsonDeserialize(using = CastIdDeserializer.class)
    private List<Integer> ids;
}

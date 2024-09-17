package app.entities.special_entities;

import app.api.custom_deserializers.MovieMultiIdDeserializer;
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
public class MovieMulti {
    @JsonProperty("results")
    @JsonDeserialize(using = MovieMultiIdDeserializer.class)
    private List<Integer> movieIds;
    @JsonProperty("total_pages")
    private int totalPages;
}

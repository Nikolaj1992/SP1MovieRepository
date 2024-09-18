package app.entities.dtos;

import app.custom_deserializers.GenreNameDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO {
    @JsonProperty("id")
    private int id;
    @JsonProperty("original_title")
    private String title;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("genres")
//    @JsonDeserialize(using = GenreNameDeserializer.class)
//    private List<String> genres;
    private List<GenreDTO> genres;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("release_date")
    private LocalDate releaseDate;
    @JsonProperty("vote_average")
    private double voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GenreDTO {
        @JsonProperty("id")
        private int id;
        @JsonProperty("name")
        private String name;
    }
}

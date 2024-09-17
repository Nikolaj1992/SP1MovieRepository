package app.entities.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("tagline")
    private String tagline;
    @JsonProperty("overview")
    private String overview;
    @JsonProperty("genres")
    private List<GenreDTO> genres;
    @JsonProperty("original_language")
    private String originalLanguage;
    @JsonProperty("homepage")
    private String homepageUrl;
    @JsonProperty("status")
    private String status;
    @JsonProperty("release_date")
    private LocalDate releaseDate;
    @JsonProperty("vote_average")
    private double voteAverage;
    @JsonProperty("vote_count")
    private int voteCount;

    public String getReleaseDateAsString() {
        return releaseDate.toString();
    }

    public String getReleaseYearAsString() {
        return releaseDate.getYear() + "";
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class GenreDTO {
        @JsonProperty("id")
        private int id;
        @JsonProperty("name")
        private String name;
    }
}

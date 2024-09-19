package app.entities.dtos;

import app.custom_deserializers.GenreNameDeserializer;
import app.entities.Movie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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

    private MovieCreditsDTO credits;

    public MovieDTO(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.overview = movie.getOverview();
        this.genres = movie.getGenres() != null ? movie.getGenres().stream()
                .map(GenreDTO::new)  // Convert Genre entities to GenreDTOs
                .collect(Collectors.toList()) : null;
        this.originalLanguage = movie.getOriginalLanguage();
        this.releaseDate = movie.getReleaseDate();
        this.voteAverage = movie.getVoteAverage();
        this.voteCount = movie.getVoteCount();
    }

}

package app.entities;

import app.entities.dtos.GenreDTO;
import app.entities.dtos.MovieDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String overview;
    @Column(name = "original_language")
    private String originalLanguage;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    @Column(name = "vote_avg")
    private double voteAverage;
    @Column(name = "votes")
    private int voteCount;

    @OneToOne
    private MovieCredits movieCredits;

    // TODO decide how to handle genres
    @ManyToMany(mappedBy = "movies", cascade = CascadeType.PERSIST)
    private List<Genre> genres;

    public Movie(MovieDTO movieDTO) {
        this.title = movieDTO.getTitle();
        this.overview = movieDTO.getOverview();
        this.originalLanguage = movieDTO.getOriginalLanguage();
        this.releaseDate = movieDTO.getReleaseDate();
        this.voteAverage = movieDTO.getVoteAverage();
        this.voteCount = movieDTO.getVoteCount();
        this.movieCredits = new MovieCredits(movieDTO.getCredits());
        this.addGenres(movieDTO.getGenres()); //think of this as adding a value to this.genres
    }

    public void addGenres(List<GenreDTO> genres) {
        if (this.genres == null) {
        List<Genre> genreList = new ArrayList<>();
        genres.forEach(genre -> genreList.add(new Genre(genre)));
        this.genres = genreList;
        genreList.forEach(genre -> genre.getMovies().add(this));
        }
    }

}

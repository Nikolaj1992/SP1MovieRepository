package app.entities;

import app.entities.dtos.GenreDTO;
import app.entities.dtos.MovieCreditsDTO;
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
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Integer id;

    private String title;
//    private String overview;
    @Column(name = "original_language")
    private String originalLanguage;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    @Column(name = "vote_avg")
    private double voteAverage;
    @Column(name = "votes")
    private int voteCount;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "movie_credits",  // Name of the join table
            joinColumns = @JoinColumn(name = "movie_id"),  // FK for Movie
            inverseJoinColumns = @JoinColumn(name = "credits_id")  // FK for MovieCredits
    )
    private MovieCredits movieCredits;

    // TODO decide how to handle genres
    @ManyToMany
    private List<Genre> genres = new ArrayList<>();

    public Movie(MovieDTO movieDTO) {
        this.title = movieDTO.getTitle();
//        this.overview = movieDTO.getOverview();
        this.originalLanguage = movieDTO.getOriginalLanguage();
        this.releaseDate = movieDTO.getReleaseDate();
        this.voteAverage = movieDTO.getVoteAverage();
        this.voteCount = movieDTO.getVoteCount();
        final MovieCredits movieCredits = new MovieCredits(movieDTO.getCredits());
        this.addMovieCredit(movieCredits);
        this.addGenres(movieDTO.getGenres()); //think of this as adding a value to this.genres
    }

    public void addGenres(List<GenreDTO> genres) {
        if (this.genres == null && genres != null) {
        List<Genre> genreList = new ArrayList<>();
        genres.forEach(genre -> genreList.add(new Genre(genre)));
        this.genres = genreList;
        genreList.forEach(genre -> genre.getMovies().add(this));
        }
    }

    public void addMovieCredit(MovieCredits movieCredits) {
        if (this.movieCredits == null && movieCredits != null) {
            this.movieCredits = movieCredits;
            this.movieCredits.setMovie(this);
        }
    }

}

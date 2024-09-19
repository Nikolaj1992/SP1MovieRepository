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
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private MovieCredits movieCredits;

    // TODO decide how to handle genres
    @ManyToMany
    private List<Genre> genres = new ArrayList<>();

    public Movie(MovieDTO movieDTO) {
        this.id = movieDTO.getId();
        this.title = movieDTO.getTitle();
//        this.overview = movieDTO.getOverview();
        this.originalLanguage = movieDTO.getOriginalLanguage();
        this.releaseDate = movieDTO.getReleaseDate();
        this.voteAverage = movieDTO.getVoteAverage();
        this.voteCount = movieDTO.getVoteCount();
        MovieCredits movieCredits = new MovieCredits(movieDTO.getCredits());
        this.addMovieCredit(movieCredits);
        List<Genre> genres = movieDTO.getGenres().stream().map(genreDTO -> new Genre(genreDTO)).toList();
        genres.forEach(System.out::println);
        this.addGenres(genres); //think of this as adding a value to this.genres
        System.out.println("-----");
        genres.forEach(System.out::println);
    }

    public void addGenres(List<Genre> genres) {
        if (this.genres == null && genres != null) {

            genres.forEach(g -> g.addMovie(this));

//            genres.forEach(genre -> genre.getMovies().add(this));
//            genres.forEach(this.genres::add);

//            for (Genre genre : genres) {
//                genre.getMovies().add(this);
//                this.genres.add(genre);
//            }

        }
    }

    public void addMovieCredit(MovieCredits movieCredits) {
        if (this.movieCredits == null && movieCredits != null) {
            this.movieCredits = movieCredits;
            this.movieCredits.setMovie(this);
        }
    }

}

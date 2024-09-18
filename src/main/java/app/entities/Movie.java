package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    private String tagline;
    private String overview;
    @Column(name = "original_language")
    private String originalLanguage;
    private String status;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    @Column(name = "vote_avg")
    private double voteAverage;
    @Column(name = "votes")
    private int voteCount;

    @ManyToMany
    @JoinTable(name = "movie_actor")
    private List<Actor> actors;

    @ManyToMany
    @JoinTable(name = "movie_director")
    private List<Director> directors;

    // TODO decide how to handle genres

}

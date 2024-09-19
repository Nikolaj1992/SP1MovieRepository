package app.entities;

import app.entities.dtos.GenreDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "genre")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Id
    private Integer id;

    private String name;

    @ManyToMany(mappedBy = "genres")
    private List<Movie> movies = new ArrayList<>();

    public Genre(GenreDTO genre) {
        this.id = genre.getId();
        this.name = genre.getName();
    }

    public void addMovie(Movie movie) {
        if (!this.movies.contains(movie)) {
            movie.getGenres().add(this);
            this.movies.add(movie);
        }
    }

}

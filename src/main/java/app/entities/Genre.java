package app.entities;

import app.entities.dtos.MovieDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "genres")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Id
    private Integer id;

    private String name;

    @ManyToMany
    private List<Movie> movies;

    public Genre(MovieDTO.GenreDTO genre) {
        this.id = genre.getId();
        this.name = genre.getName();
    }
}

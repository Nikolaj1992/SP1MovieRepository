package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "movie_director")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Director {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String job;

    @ManyToMany
    private List<Movie> movies;

}

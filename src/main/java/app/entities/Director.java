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
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //this is not needed as we get their id from the api
    private Integer id;

    private String name;
    private String job;

    @ManyToMany
    private List<Movie> movies;

}

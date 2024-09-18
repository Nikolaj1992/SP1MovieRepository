package app.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "movie_actor")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToMany     // TODO specify additional relation info such as mappedBy or excludes
    private List<Movie> movies;

}

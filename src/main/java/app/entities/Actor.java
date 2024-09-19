package app.entities;

import app.entities.dtos.ActorDTO;
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
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // fetched from the api
    private Integer id;

    private String name;

    @ManyToMany     // TODO specify additional relation info such as mappedBy or excludes
    private List<Movie> movies;

    @ManyToMany
    private List<MovieCredits> credits;

    public Actor(ActorDTO actorDTO) {
        this.id = actorDTO.getId();
        this.name = actorDTO.getName();
    }
}

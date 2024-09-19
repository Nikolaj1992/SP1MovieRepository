package app.entities;

import app.entities.dtos.DirectorDTO;
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
    private String department;

    @ManyToMany
    private List<Movie> movies;

    @ManyToMany
    private List<MovieCredits> credits;

    public Director(DirectorDTO directorDTO){
        this.id = directorDTO.getId();
        this.name = directorDTO.getName();
        this.department = directorDTO.getDepartment();
    }

}

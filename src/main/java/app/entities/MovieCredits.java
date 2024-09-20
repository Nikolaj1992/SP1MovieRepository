package app.entities;

import app.entities.dtos.ActorDTO;
import app.entities.dtos.DirectorDTO;
import app.entities.dtos.MovieCreditsDTO;
import app.entities.dtos.MovieDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MovieCredits {
    @Id
    @Column(name = "credits_id")
    private Integer id;

    // TODO: add relations here and stuff
    @ToString.Exclude
    @OneToOne(mappedBy = "movieCredits")
    private Movie movie;
    @ToString.Exclude
    @ManyToMany
    private List<Actor> actors = new ArrayList<>();
    @ToString.Exclude
    @ManyToMany
    private List<Director> directors = new ArrayList<>();

    public MovieCredits(MovieCreditsDTO movieCreditsDTO) {
        this.id = movieCreditsDTO.getId();
        List<Actor> actorList = movieCreditsDTO.getCast().stream().map(actorDTO -> new Actor(actorDTO)).toList();
        List<Director> directorList = movieCreditsDTO.getCrew().stream().map(directorDTO -> new Director(directorDTO)).toList();
        this.addActor(actorList);
        this.addDirector(directorList);
    }

    public void addActor(List<Actor> actor) {
        if (this.actors != null && !actor.isEmpty()) {
            for (Actor actor1 : actor) {
                actor1.getCredits().add(this);
                this.actors.add(actor1);
            }
        }
    }

    public void addDirector(List<Director> director) {
        if (this.directors != null && !director.isEmpty()) {
            for (Director director1 : director) {
                director1.getCredits().add(this);
                this.directors.add(director1);
            }
        }
    }

}

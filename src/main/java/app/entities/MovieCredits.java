package app.entities;

import app.entities.dtos.ActorDTO;
import app.entities.dtos.DirectorDTO;
import app.entities.dtos.MovieCreditsDTO;
import app.entities.dtos.MovieDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MovieCredits {
    @Id
    private int id;

    // TODO: add relations here and stuff
    @OneToOne
    private Movie movie;
    @ManyToMany
    private List<Actor> actors;
    @ManyToMany
    private List<Director> directors;

    public MovieCredits(MovieCreditsDTO movieCreditsDTO) {
        this.id = movieCreditsDTO.getId();
        this.movie = new Movie(movieCreditsDTO.getMovie());
        for (ActorDTO actorDTO : movieCreditsDTO.getCast()) {
            Actor actor = new Actor(actorDTO);
            actor.getCredits().add(this);
            actor.getMovies().add(this.movie);
            this.actors.add(actor);
        }
        for (DirectorDTO directorDTO : movieCreditsDTO.getCrew()){
            Director director = new Director(directorDTO);
            director.getCredits().add(this);
            director.getMovies().add(this.movie);
            this.directors.add(director);
        }
    }

}

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MovieCredits {
    @Id
    @Column(name = "credits_id")
    private Integer id;

    // TODO: add relations here and stuff
//    @OneToOne(mappedBy = "movieCredits")
    @OneToOne
    private Movie movie;
    @ManyToMany
    private List<Actor> actors = new ArrayList<>();
    @ManyToMany
    private List<Director> directors = new ArrayList<>();

    public MovieCredits(MovieCreditsDTO movieCreditsDTO) {
        this.id = movieCreditsDTO.getId();
//        final Movie movie = new Movie(movieCreditsDTO.getMovie());
//        this.addMovie(movie);
        if (this.actors == null && movieCreditsDTO != null) {
        for (ActorDTO actorDTO : movieCreditsDTO.getCast()) {
            Actor actor = new Actor(actorDTO);
            actor.getCredits().add(this);
            this.actors.add(actor);
        }
        }
        if (this.directors == null && movieCreditsDTO != null) {
        for (DirectorDTO directorDTO : movieCreditsDTO.getCrew()){
            Director director = new Director(directorDTO);
            director.getCredits().add(this);
            this.directors.add(director);
        }
        }
    }

//    public void addMovie(Movie movie) {
//        if (this.movie == null && movie != null) {
//            this.movie = movie;
//        }
//    }

}

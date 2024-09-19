package app.entities;

import app.entities.dtos.ActorDTO;
import app.entities.dtos.DirectorDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
    @ManyToMany
    private List<ActorDTO> actors;
    private List<DirectorDTO> directors;

}

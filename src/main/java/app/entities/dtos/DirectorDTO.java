package app.entities.dtos;

import app.entities.Director;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectorDTO {

    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("department") // possibly replace this with "Department"
    private String department;

    public DirectorDTO(Director director) {
        this.id = director.getId();
        this.name = director.getName();
        this.department = director.getDepartment();
    }
}

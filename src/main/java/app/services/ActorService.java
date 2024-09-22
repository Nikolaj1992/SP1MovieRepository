package app.services;

import app.dao.ActorDAO;
import app.entities.dtos.ActorDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.List;
import java.util.Map;

public class ActorService {

    private ActorDAO actorDAO;

    public ActorService(ActorDAO actorDAO) {
        this.actorDAO = actorDAO;
    }

    public ActorDTO createActor(ActorDTO actorDTO) {
        return actorDAO.create(actorDTO);
    }

    public ActorDTO findActorById(int id) {
        return actorDAO.find(id);
    }

    public ActorDTO updateActor(ActorDTO actorDTO, int id) {
        return actorDAO.update(actorDTO, id);
    }

    public int deleteActor(int id) {
        return actorDAO.delete(id);
    }

    public List<ActorDTO> findAllActors() {
        return actorDAO.findAll();
    }

    public Map<String, Object> findMoviesByActorId(Integer id) {
        return actorDAO.moviesByActorId(id);
    }

}

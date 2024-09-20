package app.services;

import app.dao.*;
import app.entities.dtos.*;

import java.util.List;

public class MultiService {

    private ActorDAO actorDAO;
    private DirectorDAO directorDAO;
    private GenreDAO genreDAO;
    private MovieDAO movieDAO;
    private MovieCreditsDAO movieCreditsDAO;

    public MultiService(ActorDAO actorDAO, DirectorDAO directorDAO, GenreDAO genreDAO,
                        MovieDAO movieDAO, MovieCreditsDAO movieCreditsDAO) {
        this.actorDAO = actorDAO;
        this.directorDAO = directorDAO;
        this.genreDAO = genreDAO;
        this.movieDAO = movieDAO;
        this.movieCreditsDAO = movieCreditsDAO;
    }

    // Similar to generics, with type casting - T = Data type, D = identifier
    public <T> T createInDB(T dto) {
        if (dto instanceof ActorDTO) {
            return (T) actorDAO.create((ActorDTO) dto);
        }
        if (dto instanceof DirectorDTO) {
            return (T) directorDAO.create((DirectorDTO) dto);
        }
        if (dto instanceof GenreDTO) {
            return (T) genreDAO.create((GenreDTO) dto);
        }
        if (dto instanceof MovieDTO) {
            return (T) movieDAO.create((MovieDTO) dto);
        }
        if (dto instanceof MovieCreditsDTO) {
            return (T) movieCreditsDAO.create((MovieCreditsDTO) dto);
        }
        return null;
    }

    public <T, D> T updateInDB(T dto, D id) {
        if (dto instanceof ActorDTO) {
            return (T) actorDAO.update((ActorDTO) dto, (Integer) id);
        }
        if (dto instanceof DirectorDTO) {
            return (T) directorDAO.update((DirectorDTO) dto, (Integer) id);
        }
        if (dto instanceof GenreDTO) {
            return (T) genreDAO.update((GenreDTO) dto, (Integer) id);
        }
        if (dto instanceof MovieDTO) {
            return (T) movieDAO.update((MovieDTO) dto, (Integer) id);
        }
        if (dto instanceof MovieCreditsDTO) {
            return (T) movieCreditsDAO.update((MovieCreditsDTO) dto, (Integer) id);
        }
        return null;
    }

    public <T, D> T findByIdInDB(Class<T> clazz, D id) {
        if (clazz == ActorDTO.class) {
            return (T) actorDAO.find((Integer) id);
        }
        if (clazz == DirectorDTO.class) {
            return (T) directorDAO.find((Integer) id);
        }
        if (clazz == GenreDTO.class) {
            return (T) genreDAO.find((Integer) id);
        }
        if (clazz == MovieDTO.class) {
            return (T) movieDAO.find((Integer) id);
        }
        if (clazz == MovieCreditsDTO.class) {
            return (T) movieCreditsDAO.find((Integer) id);
        }
        return null;
    }

    public <T> int deleteInDB(Class<T> clazz, int id) {
        if (clazz == ActorDTO.class) {
            return actorDAO.delete(id);
        }
        if (clazz == DirectorDTO.class) {
            return directorDAO.delete(id);
        }
        if (clazz == GenreDTO.class) {
            return genreDAO.delete(id);
        }
        if (clazz == MovieDTO.class) {
            return movieDAO.delete(id);
        }
        if (clazz == MovieCreditsDTO.class) {
            return movieCreditsDAO.delete(id);
        }
        return 0;
    }

    public <T> List<T> findAllInDB(Class<T> clazz) {
        if (clazz == ActorDTO.class) {
            return (List<T>) actorDAO.findAll();
        }
        if (clazz == DirectorDTO.class) {
            return (List<T>) directorDAO.findAll();
        }
        if (clazz == GenreDTO.class) {
            return (List<T>) genreDAO.findAll();
        }
        if (clazz == MovieDTO.class) {
            return (List<T>) movieDAO.findAll();
        }
        if (clazz == MovieCreditsDTO.class) {
            return (List<T>) movieCreditsDAO.findAll();
        }
        return null;
    }

}

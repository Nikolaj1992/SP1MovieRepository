package app.services;

import app.dao.DirectorDAO;
import app.entities.dtos.DirectorDTO;

import java.util.List;

public class DirectorService {

    private DirectorDAO directorDAO;

    public DirectorService(DirectorDAO directorDAO) {
        this.directorDAO = directorDAO;
    }

    public DirectorDTO createDirector(DirectorDTO directorDTO) {
        return directorDAO.create(directorDTO);
    }

    public DirectorDTO findDirectorById(int id) {
        return directorDAO.find(id);
    }

    public DirectorDTO updateDirector(DirectorDTO directorDTO, int id) {
        return directorDAO.update(directorDTO, id);
    }

    public int deleteDirector(int id) {
        return directorDAO.delete(id);
    }

    public List<DirectorDTO> findAllDirectors() {
        return directorDAO.findAll();
    }

}

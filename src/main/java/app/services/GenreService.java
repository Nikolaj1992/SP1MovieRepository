package app.services;

import app.dao.GenreDAO;
import app.entities.dtos.GenreDTO;

import java.util.List;

public class GenreService {

    private GenreDAO genreDAO;

    public GenreService(GenreDAO genreDAO) {
        this.genreDAO = genreDAO;
    }

    public GenreDTO createGenre(GenreDTO genreDTO) {
        return genreDAO.create(genreDTO);
    }

    public GenreDTO findGenreById(int id) {
        return genreDAO.find(id);
    }

    public GenreDTO updateGenre(GenreDTO genreDTO, int id) {
        return genreDAO.update(genreDTO, id);
    }

    public int deleteGenre(int id) {
        return genreDAO.delete(id);
    }

    public List<GenreDTO> findAllGenres() {
        return genreDAO.findAll();
    }

}

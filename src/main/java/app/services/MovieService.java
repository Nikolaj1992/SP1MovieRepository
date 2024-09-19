package app.services;

import app.dao.MovieDAO;
import app.entities.dtos.MovieDTO;

import java.util.List;

public class MovieService {

    private MovieDAO movieDAO;

    public MovieService(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    public MovieDTO createMovie(MovieDTO movieDTO) {
        return movieDAO.create(movieDTO);
    }

    public MovieDTO findMovieById(int id) {
        return movieDAO.find(id);
    }

    public MovieDTO updateMovie(MovieDTO movieDTO, int id) {
        return movieDAO.update(movieDTO, id);
    }

    public int deleteMovie(int id) {
        return movieDAO.delete(id);
    }

    public List<MovieDTO> findAllMovies() {
        return movieDAO.findAll();
    }

}

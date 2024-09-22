package app.services;

import app.dao.MovieCreditsDAO;
import app.entities.dtos.MovieCreditsDTO;

import java.util.List;

public class MovieCreditsService {

    private MovieCreditsDAO creditsDAO;

    public MovieCreditsService(MovieCreditsDAO creditsDAO) {
        this.creditsDAO = creditsDAO;
    }

    public MovieCreditsDTO createCredits(MovieCreditsDTO creditsDTO) {
        return creditsDAO.create(creditsDTO);
    }

    public MovieCreditsDTO findCreditsById(int id) {
        return creditsDAO.find(id);
    }

    public MovieCreditsDTO updateCredits(MovieCreditsDTO creditsDTO, int id) {
        return creditsDAO.update(creditsDTO, id);
    }

    public int deleteCredits(int id) {
        return creditsDAO.delete(id);
    }

    public List<MovieCreditsDTO> findAllCredits() {
        return creditsDAO.findAll();
    }

}

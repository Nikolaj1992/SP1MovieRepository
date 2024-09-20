package app.services;

import app.dao.MovieDAO;
import app.entities.dtos.MovieDTO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MovieService {

    private MovieDAO movieDAO;
    private List<MovieDTO> movieList= movieDAO.findAll();

    public MovieService(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    public List<MovieDTO> searchForMovieByTitle(String title) {
        List<MovieDTO> results = new ArrayList<>();
        for (MovieDTO movieDTO : movieList) {
            if (movieDTO.getTitle().toLowerCase().contains(title.toLowerCase())) {
                results.add(movieDTO);
            }
        }
        return results;
    }

    public List<MovieDTO> getHigestRatedMovies(int amountOfMovies) {
        List<MovieDTO> results = new ArrayList<>();
        results = movieList.stream().sorted(Comparator.comparing(movieDTO -> movieDTO.getVoteAverage())).limit(amountOfMovies).collect(Collectors.toList());
        return results;
    }

    public List<MovieDTO> getLowestRatedMovies(int amountOfMovies) {
        List<MovieDTO> results = new ArrayList<>();
        results = movieList.stream().sorted(Comparator.comparingDouble(MovieDTO::getVoteAverage).reversed()).limit(amountOfMovies).collect(Collectors.toList());
        return results;
    }

    public List<MovieDTO> getMostPopularMovies(int amountOfMovies) {
        List<MovieDTO> results = new ArrayList<>();
        for (MovieDTO movieDTO : movieList) {

        }
        return results;
    }

}

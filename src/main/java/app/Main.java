package app;

import app.config.HibernateConfig;
import app.dao.ApiDAO;
import app.entities.Movie;
import app.entities.dtos.MovieDTO;
import app.services.ApiReader;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        ApiReader apiReader = new ApiReader();

        apiReader.readMovieMultiple();
//        apiReader.apiMovies.forEach(System.out::println);
//        apiReader.apiCredits.forEach(System.out::println);
//        System.out.println("Amount of movies: " + apiReader.apiMovies.size());
//        System.out.println("Amount of credits: " + apiReader.apiCredits.size());
//        apiReader.apiActors.forEach(System.out::println);
//        apiReader.apiDirectors.forEach(System.out::println);
//        for (MovieDTO apiMovie : apiReader.apiMovies) {
//            if (apiMovie.getId() == 833339){
//                System.out.println(apiMovie);
//            }
//        }

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);

        List<Movie> movies = apiReader.apiMovies.stream().map(movieDTO -> new Movie(movieDTO)).toList();
        ApiDAO apiDAO = ApiDAO.getInstance(emf);
        apiDAO.persistAll(movies);
    }
}

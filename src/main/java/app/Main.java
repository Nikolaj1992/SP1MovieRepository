package app;

import app.config.HibernateConfig;
import app.dao.ActorDAO;
import app.dao.ApiDAO;
import app.dao.MovieDAO;
import app.entities.Movie;
import app.entities.dtos.ActorDTO;
import app.entities.dtos.MovieDTO;
import app.services.ActorService;
import app.services.MultiService;
import app.services.MovieService;
import app.services.api.ApiReader;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        ApiReader apiReader = new ApiReader();

//        apiReader.readMovieMultiple();
//        apiReader.apiMovies.forEach(System.out::println);
//        apiReader.apiCredits.forEach(System.out::println);
//        System.out.println("Amount of movies: " + apiReader.apiMovies.size());
//        System.out.println("Amount of credits: " + apiReader.apiCredits.size());
//        apiReader.apiActors.forEach(System.out::println);
//        apiReader.apiDirectors.forEach(System.out::println);
//        for (MovieDTO apiMovie : apiReader.apiMovies) {
//            if (apiMovie.getId() == 833339){
//                System.out.println(apiMovie.toString());
//
//            }
//        }

        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);

//        List<Movie> movies = apiReader.apiMovies.stream().map(mDTO -> new Movie(mDTO)).toList();
//        ApiDAO apiDAO = ApiDAO.getInstance(emf);
//        apiDAO.persistAll(movies);

        // It works nicely - adding an actor
        MultiService multiService = MultiService.getInstance(emf);

//        ActorDTO newActor = new ActorDTO();
//        newActor.setName("Anthony Hopkins");
//        multiService.createInDB(newActor);

      
        MovieDAO movieDAO = MovieDAO.getInstance(emf);
        MovieService movieService = new MovieService(movieDAO);
//        movieService.getHigestRatedMovies(10).forEach(movieDTO -> System.out.println("Highest: " + movieDTO.getTitle() + " - " + movieDTO.getVoteAverage()));
//        movieService.getLowestRatedMovies(10).forEach(movieDTO -> System.out.println("Lowest: " + movieDTO.getTitle() + " - " +  movieDTO.getVoteAverage()));
//        movieService.getMostPopularMovies(10).forEach(movieDTO -> System.out.println("Popular: " + movieDTO.getTitle() + " - " +  movieDTO.getVoteAverage()+ " - " +  movieDTO.getVoteCount()));
//        movieService.searchForMovieByTitle("for").forEach(movieDTO -> {System.out.println(movieDTO.getTitle());});

        // Finding movies by actor - maybe somewhat convoluted. Could be improved with controllers
        ActorDAO actorDAO = ActorDAO.getInstance(emf);
        ActorService actorService = new ActorService(actorDAO);

        actorService.findMoviesByActorId(1018).
                forEach((key, value) -> {
                    if (key.equals("actorName")) {
                    System.out.println("Actor: " + value);
                    } else if (key.equals("movies")) {
                        List<MovieDTO> movieList = (List<MovieDTO>) value;
                        movieList.forEach(movieDTO -> System.out.println("ID: " + movieDTO.getId() +
                        " - Movie: " + movieDTO.getTitle() + " - Rating: " + movieDTO.getVoteAverage()));
                    }
                });

        emf.close();
    }
}

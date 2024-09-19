package app.dao;

import app.entities.*;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class ApiDAO {
    // doesn't implement GenericDAO, as this will only be run once to persist all data from the api fetch

    private static ApiDAO instance;
    private static EntityManagerFactory emf;

    private ApiDAO() {}

    public static ApiDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ApiDAO();
        }
        return instance;
    }

    public void persistAll(List<Movie> movies) {
        try(var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            List<Genre> genres = new ArrayList<>();
            List<Actor> actors = new ArrayList<>();
            List<Director> directors = new ArrayList<>();
            for (Movie movie : movies) {
                for (Genre genre : movie.getGenres()) {
                    if (!genres.contains(genre)) {
                        genres.add(genre);
                    }
                }
                for (Actor actor : movie.getMovieCredits().getActors()) {
                    if (!actors.contains(actor)) {
                        actors.add(actor);
                    }
                }
                for (Director director : movie.getMovieCredits().getDirectors()) {
                    if (!directors.contains(director)) {
                        directors.add(director);
                    }
                }
            }

            genres.forEach(em::persist);
            actors.forEach(em::persist);
            directors.forEach(em::persist);

            for (Movie movie : movies) {
                MovieCredits movieCredits = movie.getMovieCredits();
                movieCredits.setActors(new ArrayList<>(movieCredits.getActors()));
                movieCredits.setDirectors(new ArrayList<>(movieCredits.getDirectors()));

                em.persist(movieCredits);
                em.persist(movie);
            }
            em.getTransaction().commit();
        }
    }
}

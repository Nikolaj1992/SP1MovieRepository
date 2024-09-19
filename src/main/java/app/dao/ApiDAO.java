package app.dao;

import app.entities.Actor;
import app.entities.Director;
import app.entities.Genre;
import app.entities.Movie;
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
                em.persist(movie);
                em.persist(movie.getMovieCredits());
                for (Genre genre : movie.getGenres()) { // add all genres to a list so copies can be avoided
                    if (!genres.contains(genre)) {
                        genres.add(genre);
                        em.persist(genre);
                    }
                }
                for (Actor actor : movie.getMovieCredits().getActors()) {
                    if (!actors.contains(actor)) {
                        actors.add(actor);
                        em.persist(actor);
                    }
                }
                for (Director director : movie.getMovieCredits().getDirectors()) {
                    if (!directors.contains(director)) {
                        directors.add(director);
                        em.persist(director);
                    }
                }
                genres.forEach(em::persist);
                actors.forEach(em::persist);
                directors.forEach(em::persist);
            }
            em.getTransaction().commit();
        }
    }
}

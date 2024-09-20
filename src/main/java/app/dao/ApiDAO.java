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

            for (Movie movie : movies) {
                List<Genre> genres = new ArrayList<>();
                List<Actor> actors = new ArrayList<>();
                List<Director> directors = new ArrayList<>();
                MovieCredits movieCredits = movie.getMovieCredits();

                for (Genre genre : movie.getGenres()) {
                    Genre existingGenre = em.find(Genre.class, genre.getId());
                    if (existingGenre == null) {
//                        genre.addMovie(movie);
                        em.persist(genre);
                        genres.add(genre);
                    } else {
                        genres.add(existingGenre);
                    }
//                    genre.add  my brain gave up
                }
                for (Actor actor : movie.getMovieCredits().getActors()) {
                    Actor existingActor = em.find(Actor.class, actor.getId());
                    if (existingActor == null) {
                        em.persist(actor);
                    }
                    actors.add(actor);
                }
                for (Director director : movie.getMovieCredits().getDirectors()) {
                    Director existingDirector = em.find(Director.class, director.getId());
                    if (existingDirector == null) {
                        em.persist(director);
                    }
                    directors.add(director);
                }
                movieCredits.setActors(new ArrayList<>(actors));
                movieCredits.setDirectors(new ArrayList<>(directors));
                em.persist(movieCredits);
                em.persist(movie);
            }

//            genres.forEach(em::persist);
//            actors.forEach(em::persist);
//            directors.forEach(em::persist);

            em.getTransaction().commit();
        }
    }
}

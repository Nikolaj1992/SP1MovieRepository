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

    private void persistAll(List<Movie> movies) {
        try(var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            if ( em.find(Movie.class, movies.get(0).getId()) == null ) { //should just check if the database has data
            for (Movie movie : movies) {
                List<Genre> genres = new ArrayList<>();
                List<Actor> actors = new ArrayList<>();
                List<Director> directors = new ArrayList<>();
                MovieCredits movieCredits = movie.getMovieCredits();

                for (Genre genre : movie.getGenres()) {
                    Genre existingGenre = em.find(Genre.class, genre.getId());
                    if (existingGenre == null) {
                        em.persist(genre);
                        genres.add(genre);
                    } else {
                        genres.add(existingGenre);
                    }
                }
                for (Actor actor : movie.getMovieCredits().getActors()) {
                    if (actor != null) {
                        Actor existingActor = em.find(Actor.class, actor.getId());
                        if (existingActor == null) {
                            em.persist(actor);
                            actors.add(actor);
                        }
                        actors.add(existingActor);
                    }
                }
                for (Director director : movie.getMovieCredits().getDirectors()) {
                    if (director != null) {
                        Director existingDirector = em.find(Director.class, director.getId());
                        if (existingDirector == null) {
                            em.persist(director);
                            directors.add(director);
                        }
                        directors.add(existingDirector);
                    }
                }
                movieCredits.setActors(new ArrayList<>(actors));
                movieCredits.setDirectors(new ArrayList<>(directors));
                em.persist(movieCredits);
                em.persist(movie);
            }
            em.getTransaction().commit();
            System.out.println("ApiDAO: saved data to database");
            } //end of the query check
            else {
            System.out.println("ApiDAO: database already contains data");
            }
        }
    }

    public void persistAndUpdateAll(List<Movie> movies) {
        try(var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            List<Movie> moviesDB = em.createQuery("SELECT m FROM Movie m").getResultList();
            List<Integer> moviesDbIds = moviesDB.stream().map(Movie::getId).toList();
            List<Integer> moviesIds = movies.stream().map(Movie::getId).toList();
            List<Movie> moviesToPersist = new ArrayList<>();
            if (moviesDB.isEmpty()){
                this.persistAll(movies);
            } else {
                for (int i = 0; i < movies.size(); i++) {
                    if (!moviesDbIds.contains(movies.get(i).getId())) {
                        moviesToPersist.add(movies.get(i));
                    } else {
                    if (i > moviesDbIds.size()) {
                        moviesToPersist.add(movies.get(i));
                    }
                    }
                }
            this.persistNewMovies(moviesToPersist);
            em.getTransaction().commit();
            if (!moviesToPersist.isEmpty()){
            System.out.println("ApiDAO: updated database");
            } else {
                System.out.println("APIDAO: no new data provided, database is up to date");
            }
            }
        }
    }

    private void persistNewMovies(List<Movie> movies) {
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
                            em.persist(genre);
                            genres.add(genre);
                        } else {
                            genres.add(existingGenre);
                        }
                    }
                    for (Actor actor : movie.getMovieCredits().getActors()) {
                        if (actor != null) {
                        Actor existingActor = em.find(Actor.class, actor.getId());
                        if (existingActor == null) {
                            em.persist(actor);
                            actors.add(actor);
                        }
                        actors.add(existingActor);
                        }
                    }
                    for (Director director : movie.getMovieCredits().getDirectors()) {
                        if (director != null) {
                        Director existingDirector = em.find(Director.class, director.getId());
                        if (existingDirector == null) {
                            em.persist(director);
                            directors.add(director);
                        }
                        directors.add(existingDirector);
                        }
                    }
                    movieCredits.setActors(new ArrayList<>(actors));
                    movieCredits.setDirectors(new ArrayList<>(directors));
                    em.persist(movieCredits);
                    em.persist(movie);
                }
                em.getTransaction().commit();
                if (!movies.isEmpty()){
                System.out.println("ApiDAO-updateAll(persist): saved data to database");
                }
            }
        }
    }

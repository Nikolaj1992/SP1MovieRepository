package app.dao;

import app.config.HibernateConfig;
import app.entities.Actor;
import app.entities.Director;
import app.entities.Movie;
import app.entities.MovieCredits;
import app.entities.dtos.ActorDTO;
import app.entities.dtos.DirectorDTO;
import app.entities.dtos.MovieCreditsDTO;
import app.entities.dtos.MovieDTO;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MovieDAOTest {

    private static EntityManagerFactory emf;
    private static MovieDAO mDao;
    private static ActorDAO aDao;
    private static DirectorDAO dDao;
    private static MovieCreditsDAO cDao;

    @BeforeAll
    static void setUpAll() {
        emf = HibernateConfig.getEntityManagerFactoryConfig(true);
        mDao = MovieDAO.getInstance(emf);
    }

    @BeforeEach
    void setUp() {
        Actor actor1 = new Actor(1, "Carl Ottosen", new ArrayList<>());
        Actor actor2 = new Actor(2, "Ebbe Langberg", new ArrayList<>());
        Actor actor3 = new Actor(3, "Dirch Passer", new ArrayList<>());
        Actor actor4 = new Actor(4, "Klaus Pagh", new ArrayList<>());
        Director director = new Director(1, "Sven Methling", "Directing", new ArrayList<>());

        MovieCredits credits = new MovieCredits();
        credits.setId(1);
        credits.addActor(List.of(actor1, actor2, actor3, actor4));
        credits.addDirector(List.of(director));

        Movie movie = new Movie();
        movie.setId(1);
        movie.setTitle("Soldaterkammerater");
        movie.setOriginalLanguage("da");
        movie.setReleaseDate(LocalDate.of(1958, 10, 17));
        movie.setVoteAverage(6.3);
        movie.setVoteCount(453);
        movie.addMovieCredit(credits); // Use this method to establish the relationship

        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Movie").executeUpdate();
            em.createQuery("DELETE FROM Actor").executeUpdate();
            em.createQuery("DELETE FROM Director").executeUpdate();
            em.createQuery("DELETE FROM MovieCredits").executeUpdate();
            em.persist(actor1);
            em.persist(actor2);
            em.persist(actor3);
            em.persist(actor4);
            em.persist(director);
            em.persist(movie);
            em.getTransaction().commit();
        }
    }

    @Test
    void create() {
        MovieDTO newMovie = mDao.find(1);

        assertNotNull(newMovie);
        assertEquals("Soldaterkammerater", newMovie.getTitle());
        System.out.println(newMovie);       // credits is null. Why?
    }

    @Test
    void delete() {
        MovieDTO movieForDeletion = mDao.find(1);
        mDao.delete(movieForDeletion.getId());

        assertNull(movieForDeletion);

        // Check that deleting movie also deletes credits
        List<MovieCreditsDTO> credits = cDao.findAll();
        assertTrue(credits.isEmpty());

        // Check that actors and directors remain
        List<ActorDTO> actors = aDao.findAll();
        List<DirectorDTO> directors = dDao.findAll();
        assertFalse(actors.isEmpty());
        assertFalse(directors.isEmpty());
    }

    @Test
    void find() {
        MovieDTO foundMovie = mDao.find(1);

        assertNotNull(foundMovie);
    }

    @Test
    void update() {
    }

    @Test
    void findAll() {
        List<MovieDTO> allMovies = mDao.findAll();

        int expectedListSize = 1;
        assertEquals(expectedListSize, allMovies.size());
    }
}
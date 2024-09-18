package app.dao;

import app.entities.Movie;
import app.entities.dtos.MovieDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MovieDAO {

    ObjectMapper om = new ObjectMapper();

    private static MovieDAO instance;
    private static EntityManagerFactory emf;

    private MovieDAO(EntityManagerFactory emf) {
        this.emf = emf;
        this.om = new ObjectMapper();
        this.om.registerModule(new JavaTimeModule());
        this.om.findAndRegisterModules();
    }

    public static MovieDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new MovieDAO(emf);
        }
        return instance;
    }

    // Helt basic, metoderne skal naturligvis opdateres/ændres

    public String getAllAsJSON() {
        List<Movie> movies = findAll();
        try {
            return om.writeValueAsString(movies);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert movies to JSON", e);
        }
    }

    public Movie create(Movie entity) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        }
        return entity;
    }

    public int delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Movie deletedMovie = em.find(Movie.class, id);
            em.getTransaction().begin();
            em.remove(deletedMovie);
            em.getTransaction().commit();
            return deletedMovie.getId();    // will work once Movie class has annotations
        }
    }

    public Movie find(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Movie.class, id);
        }
    }

    public Movie update(Movie entity, Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Movie updatedMovie = em.merge(entity);
            em.getTransaction().commit();
            return updatedMovie;
        }
    }

    public List<Movie> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Movie> query = em.createQuery("select a from Movie a", Movie.class);
            return query.getResultList();
        }
    }

}

package app.dao;

import app.entities.Movie;
import app.entities.dtos.MovieDTO;
import app.exceptions.DaoException;
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
            return entity;
        } catch (Exception e) {
            throw new DaoException.MovieCreateException(entity, e);
        }
    }

    public int delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Movie deletedMovie = em.find(Movie.class, id);
            if (deletedMovie == null) {
                throw new DaoException.MovieNotFoundException(id);
            }
            em.getTransaction().begin();
            em.remove(deletedMovie);
            em.getTransaction().commit();
            return deletedMovie.getId();    // will work once Movie class has annotations
        } catch (Exception e) {
            throw new DaoException.MovieDeleteException(id, e);
        }
    }

    public Movie find(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Movie movie = em.find(Movie.class, id);
            if (movie == null) {
                throw new DaoException.MovieNotFoundException(id);
            }
            return movie;
        }
    }

    public Movie update(Movie entity, Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Movie updatedMovie = em.merge(entity);
            em.getTransaction().commit();
            return updatedMovie;
        } catch (Exception e) {
            throw new DaoException.MovieUpdateException(id, e);
        }
    }

    public List<Movie> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Movie> query = em.createQuery("select a from Movie a", Movie.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException.MovieFindAllException(e);
        }
    }

}

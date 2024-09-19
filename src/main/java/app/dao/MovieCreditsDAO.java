package app.dao;

import app.entities.Actor;
import app.entities.Director;
import app.entities.Movie;
import app.entities.MovieCredits;
import app.entities.dtos.ActorDTO;
import app.entities.dtos.MovieCreditsDTO;
import app.entities.dtos.MovieDTO;
import app.exceptions.DaoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.stream.Collectors;

public class MovieCreditsDAO implements GenericDAO<MovieCreditsDTO, Integer> {

    private static MovieCreditsDAO instance;
    private static EntityManagerFactory emf;

    private MovieCreditsDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static MovieCreditsDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new MovieCreditsDAO(emf);
        }
        return instance;
    }

    @Override
    public MovieCreditsDTO create(MovieCreditsDTO mcDto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            MovieCredits credits = new MovieCredits(mcDto);
            em.persist(credits);
            em.getTransaction().commit();
            return mcDto;
        } catch (Exception e) {
            throw new DaoException.EntityCreateException(MovieCredits.class, e);
        }
    }

    @Override
    public int delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            MovieCredits deletedCredits = em.find(MovieCredits.class, id);
            if (deletedCredits == null) {
                throw new DaoException.EntityNotFoundException(MovieCredits.class, id);
            }
            em.getTransaction().begin();
            em.remove(deletedCredits);
            em.getTransaction().commit();
            return deletedCredits.getId();
        } catch (Exception e) {
            throw new DaoException.EntityDeleteException(MovieCredits.class, id, e);
        }
    }

    @Override
    public MovieCreditsDTO find(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            MovieCredits credits = em.find(MovieCredits.class, id);
            if (credits == null) {
                throw new DaoException.EntityNotFoundException(MovieCredits.class, id);
            }
            return new MovieCreditsDTO(credits);
        }
    }

    @Override
    public MovieCreditsDTO update(MovieCreditsDTO mcDto, Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            MovieCredits credits = em.find(MovieCredits.class, id);
            if (credits == null) {
                throw new DaoException.EntityNotFoundException(MovieCredits.class, id);
            }
            credits.setActors(mcDto.getCast().stream()
                    .map(actorDTO -> new Actor(actorDTO))
                    .collect(Collectors.toList()));

            credits.setDirectors(mcDto.getCrew().stream()
                    .map(directorDTO -> new Director(directorDTO))
                    .collect(Collectors.toList()));

            // Potentially, we could implement if statements to update genres, actor and directors here too

            MovieCredits updatedCredits = em.merge(credits);
            em.getTransaction().commit();

            return new MovieCreditsDTO(updatedCredits);
        } catch (Exception e) {
            throw new DaoException.EntityUpdateException(MovieCredits.class, id, e);
        }
    }

    @Override
    public List<MovieCreditsDTO> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<MovieCredits> query = em.createQuery("select a from MovieCredits a", MovieCredits.class);
            List<MovieCredits> credits = query.getResultList();
            return credits.stream()
                    .map(MovieCreditsDTO::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DaoException.EntityFindAllException(MovieCredits.class, e);
        }
    }

}

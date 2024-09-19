package app.dao;

import app.entities.Actor;
import app.entities.Genre;
import app.entities.Movie;
import app.entities.dtos.ActorDTO;
import app.entities.dtos.GenreDTO;
import app.entities.dtos.MovieDTO;
import app.exceptions.DaoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.stream.Collectors;

public class GenreDAO implements GenericDAO<GenreDTO, Integer> {

    private static GenreDAO instance;
    private static EntityManagerFactory emf;

    private GenreDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static GenreDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new GenreDAO(emf);
        }
        return instance;
    }

    @Override
    public GenreDTO create(GenreDTO gDto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Genre genre = new Genre(gDto);
            em.persist(genre);
            em.getTransaction().commit();
            return gDto;
        } catch (Exception e) {
            throw new DaoException.EntityCreateException(Genre.class, e);
        }
    }

    @Override
    public int delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Genre deletedGenre = em.find(Genre.class, id);
            if (deletedGenre == null) {
                throw new DaoException.EntityNotFoundException(Genre.class, id);
            }
            em.getTransaction().begin();
            em.remove(deletedGenre);
            em.getTransaction().commit();
            return deletedGenre.getId();
        } catch (Exception e) {
            throw new DaoException.EntityDeleteException(Genre.class, id, e);
        }
    }

    @Override
    public GenreDTO find(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Genre genre = em.find(Genre.class, id);
            if (genre == null) {
                throw new DaoException.EntityNotFoundException(Genre.class, id);
            }
            return new GenreDTO(genre);
        }
    }

    @Override
    public GenreDTO update(GenreDTO gDto, Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Genre genre = em.find(Genre.class, id);
            if (genre == null) {
                throw new DaoException.EntityNotFoundException(Genre.class, id);
            }
            genre.setName(gDto.getName());

            // Potentially, we could implement if statements to update genres, actor and directors here too

            Genre updatedGenre = em.merge(genre);
            em.getTransaction().commit();

            return new GenreDTO(updatedGenre);
        } catch (Exception e) {
            throw new DaoException.EntityUpdateException(Genre.class, id, e);
        }
    }

    @Override
    public List<GenreDTO> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Genre> query = em.createQuery("select a from Genre a", Genre.class);
            List<Genre> genres = query.getResultList();
            return genres.stream()
                    .map(GenreDTO::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DaoException.EntityFindAllException(Genre.class, e);
        }
    }

}

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
import java.util.stream.Collectors;

public class MovieDAO implements GenericDAO<MovieDTO, Integer> {

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

    public String getAllAsJSON() {
        List<MovieDTO> movies = findAll();
        try {
            return om.writeValueAsString(movies);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert movies to JSON", e);
        }
    }

    // TODO dao methods should accept and return DTOs, once we have our entities and DTOs fully done, edit these
    // TODO an idea could be to use Jons example from the ActivityLogger solution?

    @Override
    public MovieDTO create(MovieDTO mDto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Movie movie = new Movie(mDto);
            em.persist(movie);
            em.getTransaction().commit();
            return mDto;
        } catch (Exception e) {
            throw new DaoException.MovieCreateException(mDto, e);
        }
    }

    @Override
    public int delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Movie deletedMovie = em.find(Movie.class, id);
            if (deletedMovie == null) {
                throw new DaoException.MovieNotFoundException(id);
            }
            em.getTransaction().begin();
            em.remove(deletedMovie);
            em.getTransaction().commit();
            return deletedMovie.getId();
        } catch (Exception e) {
            throw new DaoException.MovieDeleteException(id, e);
        }
    }

    @Override
    public MovieDTO find(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Movie movie = em.find(Movie.class, id);
            if (movie == null) {
                throw new DaoException.MovieNotFoundException(id);
            }
            return new MovieDTO(movie);
        }
    }

    @Override
    public MovieDTO update(MovieDTO mDto, Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Movie movie = em.find(Movie.class, id);
            if (movie == null) {
                throw new DaoException.MovieNotFoundException(id);
            }
            movie.setTitle(mDto.getTitle());
            movie.setOverview(mDto.getOverview());
            movie.setOriginalLanguage(mDto.getOriginalLanguage());
            movie.setReleaseDate(mDto.getReleaseDate());
            movie.setVoteAverage(mDto.getVoteAverage());
            movie.setVoteCount(mDto.getVoteCount());

            // Potentially, we could implement if statements to update genres, actor and directors here too

            Movie updatedMovie = em.merge(movie);
            em.getTransaction().commit();

            return new MovieDTO(updatedMovie);
        } catch (Exception e) {
            throw new DaoException.MovieUpdateException(id, e);
        }
    }

    @Override
    public List<MovieDTO> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Movie> query = em.createQuery("select a from Movie a", Movie.class);
            List<Movie> movies = query.getResultList();
            return movies.stream()
                    .map(MovieDTO::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DaoException.MovieFindAllException(e);
        }
    }

}

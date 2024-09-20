package app.dao;

import app.entities.Director;
import app.entities.dtos.DirectorDTO;
import app.exceptions.DaoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.stream.Collectors;

public class DirectorDAO implements GenericDAO<DirectorDTO, Integer> {

    private static DirectorDAO instance;
    private static EntityManagerFactory emf;

    private DirectorDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static DirectorDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new DirectorDAO(emf);
        }
        return instance;
    }

    @Override
    public DirectorDTO create(DirectorDTO dDto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Director director = new Director(dDto);
            em.persist(director);
            em.getTransaction().commit();
            return dDto;
        } catch (Exception e) {
            throw new DaoException.EntityCreateException(Director.class, e);
        }
    }

    @Override
    public int delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Director deletedDirector = em.find(Director.class, id);
            if (deletedDirector == null) {
                throw new DaoException.EntityNotFoundException(Director.class, id);
            }
            em.getTransaction().begin();
            em.remove(deletedDirector);
            em.getTransaction().commit();
            return deletedDirector.getId();
        } catch (Exception e) {
            throw new DaoException.EntityDeleteException(Director.class, id, e);
        }
    }

    @Override
    public DirectorDTO find(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Director director = em.find(Director.class, id);
            if (director == null) {
                throw new DaoException.EntityNotFoundException(Director.class, id);
            }
            return new DirectorDTO(director);
        }
    }

    @Override
    public DirectorDTO update(DirectorDTO dDto, Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Director director = em.find(Director.class, id);
            if (director == null) {
                throw new DaoException.EntityNotFoundException(Director.class, id);
            }
            director.setName(dDto.getName());
            director.setDepartment(dDto.getDepartment());

            // Potentially, we could implement if statements to update genres, actor and directors here too

            Director updatedDirector = em.merge(director);
            em.getTransaction().commit();

            return new DirectorDTO(updatedDirector);
        } catch (Exception e) {
            throw new DaoException.EntityUpdateException(Director.class, id, e);
        }
    }

    @Override
    public List<DirectorDTO> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Director> query = em.createQuery("select a from Director a", Director.class);
            List<Director> directors = query.getResultList();
            return directors.stream()
                    .map(DirectorDTO::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DaoException.EntityFindAllException(Director.class, e);
        }
    }

}

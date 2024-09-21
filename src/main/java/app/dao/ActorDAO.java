package app.dao;

import app.entities.Actor;
import app.entities.MovieCredits;
import app.entities.dtos.ActorDTO;
import app.entities.dtos.MovieDTO;
import app.exceptions.DaoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ActorDAO implements GenericDAO<ActorDTO, Integer> {

    private static ActorDAO instance;
    private static EntityManagerFactory emf;

    private ActorDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static ActorDAO getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new ActorDAO(emf);
        }
        return instance;
    }

    @Override
    public ActorDTO create(ActorDTO aDto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Actor actor = new Actor(aDto);
            em.persist(actor);
            em.getTransaction().commit();
            return aDto;
        } catch (Exception e) {
            throw new DaoException.EntityCreateException(Actor.class, e);
        }
    }

    @Override
    public int delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Actor deletedActor = em.find(Actor.class, id);
            if (deletedActor == null) {
                throw new DaoException.EntityNotFoundException(Actor.class, id);
            }
            em.getTransaction().begin();
            em.remove(deletedActor);
            em.getTransaction().commit();
            return deletedActor.getId();
        } catch (Exception e) {
            throw new DaoException.EntityDeleteException(Actor.class, id, e);
        }
    }

    @Override
    public ActorDTO find(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Actor actor = em.find(Actor.class, id);
            if (actor == null) {
                throw new DaoException.EntityNotFoundException(Actor.class, id);
            }
            return new ActorDTO(actor);
        }
    }

    @Override
    public ActorDTO update(ActorDTO aDto, Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Actor actor = em.find(Actor.class, id);
            if (actor == null) {
                throw new DaoException.EntityNotFoundException(Actor.class, id);
            }
            actor.setName(aDto.getName());

            // Potentially, we could implement if statements to update genres, actor and directors here too

            Actor updatedActor = em.merge(actor);
            em.getTransaction().commit();

            return new ActorDTO(updatedActor);
        } catch (Exception e) {
            throw new DaoException.EntityUpdateException(Actor.class, id, e);
        }
    }

    @Override
    public List<ActorDTO> findAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Actor> query = em.createQuery("select a from Actor a", Actor.class);
            List<Actor> actors = query.getResultList();
            return actors.stream()
                    .map(ActorDTO::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DaoException.EntityFindAllException(Actor.class, e);
        }
    }

    public Map<String, Object> moviesByActorId(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            String jpql = "select a from Actor  a join fetch a.credits c join fetch c.movie m where a.id = :actorId";

            Actor actor = em.createQuery(jpql, Actor.class)
                    .setParameter("actorId", id)
                    .getSingleResult();
            if (actor != null) {
                List<MovieDTO> movieDTOS = actor.getCredits()
                        .stream()
                        .map(MovieCredits::getMovie)
                        .map(MovieDTO::new)
                        .collect(Collectors.toList());

                Map<String, Object> result = new HashMap<>();
                result.put("actorName", actor.getName());
                result.put("movies", movieDTOS);

                return result;
            }
            return Collections.emptyMap();
        }
    }

}

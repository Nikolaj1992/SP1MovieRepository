package app.dao;

import app.config.HibernateConfig;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovieDAOTest {

    private static EntityManagerFactory emf;
    private static MovieDAO mDao;

    @BeforeAll
    static void setUpAll() {
        emf = HibernateConfig.getEntityManagerFactoryConfig(true);
        mDao = MovieDAO.getInstance(emf);
    }

    @BeforeEach
    void setUp() {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Movie").executeUpdate();
//            em.persist(entity);
            em.getTransaction().commit();
        }
    }

    @Test
    void create() {
    }

    @Test
    void delete() {
    }

    @Test
    void find() {
    }

    @Test
    void update() {
    }

    @Test
    void findAll() {
    }
}
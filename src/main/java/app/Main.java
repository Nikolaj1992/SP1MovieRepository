package app;

import app.api.ApiReader;
import app.config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {

        String apiKey = System.getenv("TMDB_API_KEY");
        String url = "https://api.themoviedb.org/3/movie/💥?api_key=#";
        String fullUrl = url.replace("#",apiKey);
        fullUrl = fullUrl.replace("💥","533535");
        System.out.println(fullUrl);

        ApiReader reader = new ApiReader();
        reader.read(fullUrl);

//        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig(false);
//        EntityManager em = emf.createEntityManager();

    }
}

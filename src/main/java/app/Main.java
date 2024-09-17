package app;

import app.api.ApiReader;
import app.entities.dtos.MovieDTO;

import java.util.List;

public class Main {
    public static void main(String[] args) {

//        String apiKey = System.getenv("TMDB_API_KEY");
//        String url = "https://api.themoviedb.org/3/movie/💥?api_key=#";
//        String fullUrl = url.replace("#",apiKey);
//        fullUrl = fullUrl.replace("💥","533535");
//        System.out.println(apiKey);
//        System.out.println(fullUrl);
//
//        String urlMulti = "https://api.themoviedb.org/3/discover/movie?api_key=#&with_original_language=da&region=DK&page=!";
//        String fullUrlMulti = urlMulti.replace("#",apiKey);
//        fullUrlMulti = fullUrlMulti.replace("!","1");
//        System.out.println(fullUrlMulti);

        ApiReader apiReader = new ApiReader();
//        apiReader.readMultiple(fullUrlMulti);
        List<MovieDTO> movies = apiReader.readMovieMultiple();
        movies.forEach(System.out::println);
        apiReader.apiCasts.forEach(System.out::println);
        System.out.println("Amount of movies: " + movies.size());
        System.out.println("Amount of casts: " + apiReader.apiCasts.size());

    }
}

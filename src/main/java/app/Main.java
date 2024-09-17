package app;

import app.api.ApiReader;
import app.entities.dtos.MovieDTO;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        ApiReader apiReader = new ApiReader();

        List<MovieDTO> movies = apiReader.readMovieMultiple();
        movies.forEach(System.out::println);
        apiReader.apiCasts.forEach(System.out::println);
        System.out.println("Amount of movies: " + movies.size());
        System.out.println("Amount of casts: " + apiReader.apiCasts.size());

    }
}

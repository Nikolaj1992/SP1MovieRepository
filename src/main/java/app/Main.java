package app;

import app.entities.dtos.MovieCreditsDTO;
import app.entities.dtos.MovieDTO;
import app.services.ApiReader;

public class Main {
    public static void main(String[] args) {

        ApiReader apiReader = new ApiReader();

        apiReader.readMovieMultiple();
//        apiReader.apiMovies.forEach(System.out::println);
//        apiReader.apiCredits.forEach(System.out::println);
        System.out.println("Amount of movies: " + apiReader.apiMovies.size());
        System.out.println("Amount of credits: " + apiReader.apiCredits.size());
//        apiReader.apiActors.forEach(System.out::println);
//        apiReader.apiDirectors.forEach(System.out::println);
        for (MovieDTO apiMovie : apiReader.apiMovies) {
            if (apiMovie.getId() == 833339){
                System.out.println(apiMovie);
            }
        }

    }
}

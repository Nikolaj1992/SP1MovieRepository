package app;

import app.api.ApiReader;

public class Main {
    public static void main(String[] args) {

        ApiReader apiReader = new ApiReader();

        apiReader.readMovieMultiple();
        apiReader.apiMovies.forEach(System.out::println);
        apiReader.apiCredits.forEach(System.out::println);
        System.out.println("Amount of movies: " + apiReader.apiMovies.size());
        System.out.println("Amount of credits: " + apiReader.apiCredits.size());

    }
}

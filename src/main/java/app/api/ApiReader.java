package app.api;

import app.constants.LinkConstants;
import app.entities.MovieCast;
import app.entities.dtos.MovieDTO;
import app.entities.special_entities.MovieMulti;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiReader {
    ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());
    final String API_KEY = System.getenv("TMDB_API_KEY");
    public List<MovieCast> apiCasts = new ArrayList<>();
    public List<MovieDTO> apiMovies = new ArrayList<>();

    public List<MovieDTO> readMovieMultiple(){
        ApiReader apiReader = new ApiReader();
        String urlMulti = LinkConstants.MOVIE_MULTI_LINK.replace("#",API_KEY);
        urlMulti = urlMulti.replace("!","1");
        MovieMulti movieMulti = null;
        List<MovieDTO> movies = new ArrayList<>();
        try {
            // Create an HttpClient instance
            HttpClient client = HttpClient.newHttpClient();

            // Create a request
            HttpRequest request1 = HttpRequest.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .uri(new URI(urlMulti))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

            // Check the status code and print the response
            if (response1.statusCode() == 200) {
                String body = response1.body();
//                System.out.println(body);
//                System.out.println("--------------------");
                movieMulti = apiReader.jsonToDtoMulti(body);
//                System.out.println(movieMulti);
            } else {
                System.out.println("Multi: GET request failed. Status code: " + response1.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < movieMulti.getMovieIds().size(); i++) {
//            System.out.println(movieMulti.getMovieIds().get(i));
            movies.add(readMovieSingleById(String.valueOf(movieMulti.getMovieIds().get(i))));
//            System.out.println(apiReader.readCastByMovieId(String.valueOf(movieMulti.getMovieIds().get(i))));
            apiCasts.add(apiReader.readCastByMovieId(String.valueOf(movieMulti.getMovieIds().get(i))));
        }
//        for (int i = 0; i < movieMulti.getTotalPages(); i++) { //run this 1 less time because the first/original request is page one
//            System.out.println(movieMulti.getMovieIds().get(i));
//        }

        return movies;
    }

    public MovieDTO readMovieSingleById(String id) {
        ApiReader apiReader = new ApiReader();
        MovieDTO movie = null;
        String urlSingle = LinkConstants.MOVIE_SINGLE_LINK.replace("#",API_KEY);
        urlSingle = urlSingle.replace("!",id);
        try {
            // Create an HttpClient instance
            HttpClient client = HttpClient.newHttpClient();

            // Create a request
            HttpRequest request1 = HttpRequest.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .uri(new URI(urlSingle))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

            // Check the status code and print the response
            if (response1.statusCode() == 200) {
                String body = response1.body();
//                System.out.println(body);
//                System.out.println("--------------------");
                movie = apiReader.jsonToDtoSingle(body);
//                System.out.println(movie);
            } else {
                System.out.println("Single: GET request failed. Status code: " + response1.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movie;
    }

    public MovieCast readCastByMovieId(String id) { //incomplete method
        ApiReader apiReader = new ApiReader();
        MovieCast cast = null;
        String urlPeople = LinkConstants.MOVIE_CAST_LINK.replace("#",API_KEY);
        urlPeople = urlPeople.replace("!",id);
        try {
            // Create an HttpClient instance
            HttpClient client = HttpClient.newHttpClient();

            // Create a request
            HttpRequest request1 = HttpRequest.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .uri(new URI(urlPeople))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

            // Check the status code and print the response
            if (response1.statusCode() == 200) {
                String body = response1.body();
//                System.out.println(body);
//                System.out.println("--------------------");
                cast = apiReader.jsonToCastDtoSingle(body);
//                System.out.println(cast);
            } else {
                System.out.println("People: GET request failed. Status code: " + response1.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO: create ActorDTOs and DirectorDTOs with checks, using yet another method down here

        return cast;
    }

    private MovieDTO jsonToDtoSingle(String jsonString){
        try {
//            return om.readValue(jsonString, MovieDTO[].class)[0]; //use this if you get an array with one object
            return om.readValue(jsonString, MovieDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private MovieMulti jsonToDtoMulti(String jsonString){
        try {
            return om.readValue(jsonString, MovieMulti.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private MovieCast jsonToCastDtoSingle(String jsonString){
        try {
            return om.readValue(jsonString, MovieCast.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

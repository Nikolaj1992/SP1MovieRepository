package app.services;

import app.constants.LinkConstants;
import app.entities.MovieCredits;
import app.entities.dtos.ActorDTO;
import app.entities.dtos.DirectorDTO;
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
    public List<MovieCredits> apiCredits = new ArrayList<>();
    public List<MovieDTO> apiMovies = new ArrayList<>();
    public List<ActorDTO> apiActors = new ArrayList<>(); //int because im too lazy to currently remove the deserializers
    public List<DirectorDTO> apiDirectors = new ArrayList<>(); //int because im too lazy to currently remove the deserializers
    // TODO: make classes void methods since the lists are stored on the class instance

    public List<MovieDTO> readMovieMultiple(){
        ApiReader apiReader = new ApiReader();
        String urlMulti = LinkConstants.MOVIE_MULTI_LINK.replace("#",API_KEY);
        urlMulti = urlMulti.replace("!","1");
        MovieMulti movieMulti = null;
        List<MovieDTO> movies = new ArrayList<>();
        int totalPages = 0;
        if (totalPages == 0) {
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
//                    totalPages = movieMulti.getTotalPages();
                    totalPages = 3;
                    System.out.println("Total Pages: " + totalPages);
                } else {
                    System.out.println("Multi: GET request failed. Status code: " + response1.statusCode());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Currently on page: 1 out of " + totalPages);
            for (int i = 0; i < movieMulti.getMovieIds().size(); i++) {
//            System.out.println(movieMulti.getMovieIds().get(i));
                movies.add(readMovieSingleById(String.valueOf(movieMulti.getMovieIds().get(i))));
//            System.out.println(apiReader.readCastByMovieId(String.valueOf(movieMulti.getMovieIds().get(i))));
                apiMovies.add(readMovieSingleById(String.valueOf(movieMulti.getMovieIds().get(i))));
                apiCredits.add(apiReader.readCreditsByMovieId(String.valueOf(movieMulti.getMovieIds().get(i))));
            }
        } if (totalPages > 1) {
            for (int j = 2; j < totalPages+1; j++) {
                String jString = String.valueOf(j);
                String jStringPrevious = String.valueOf(j-1);
                System.out.println("Currently on page: " + jString + " out of " + totalPages);
                urlMulti = urlMulti.replace("page="+jStringPrevious,"page="+jString);
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
                        movieMulti = apiReader.jsonToDtoMulti(body);
                    } else {
                        System.out.println("Multi: GET request failed. Status code: " + response1.statusCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < movieMulti.getMovieIds().size(); i++) {
                    movies.add(readMovieSingleById(String.valueOf(movieMulti.getMovieIds().get(i))));
                    apiMovies.add(readMovieSingleById(String.valueOf(movieMulti.getMovieIds().get(i))));
                    apiCredits.add(apiReader.readCreditsByMovieId(String.valueOf(movieMulti.getMovieIds().get(i))));
                }
            }
        System.out.println("Finished fetching all " + totalPages + " pages");
        }

        //TODO: create ActorDTOs and DirectorDTOs with checks, using yet another method down here
        for (MovieCredits credit : apiCredits) {
            credit.getCastIds().forEach(integer -> apiActors.add(new ActorDTO(integer, "placeholder_name", "placeholder_character")));
            credit.getCrewIds().forEach(integer -> apiDirectors.add(new DirectorDTO(integer, "placeholder_name", "placeholder_job")));
            //once the serializer is detached we can do a check on credit.getCrew().getDepartment(); and then get the ones that says "Directing" or somethin
            // TODO: add these onto movies, they are currently in a huge list that isnt sorted with no connection to a movie
            // TODO: follow up from above, this can be avoided IF we make a lot of requests to get results for a single actor at the time
            System.out.println("check");
        }

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

    public MovieCredits readCreditsByMovieId(String id) { //incomplete method
        ApiReader apiReader = new ApiReader();
        MovieCredits cast = null;
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

    private MovieCredits jsonToCastDtoSingle(String jsonString){
        try {
            return om.readValue(jsonString, MovieCredits.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

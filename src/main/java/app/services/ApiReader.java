package app.services;

import app.constants.LinkConstants;
import app.entities.dtos.ActorDTO;
import app.entities.dtos.DirectorDTO;
import app.entities.dtos.MovieCreditsDTO;
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
    public List<MovieCreditsDTO> apiCredits = new ArrayList<>();
    public List<MovieDTO> apiMovies = new ArrayList<>();
    public List<ActorDTO> apiActors = new ArrayList<>(); //int because im too lazy to currently remove the deserializers
    public List<DirectorDTO> apiDirectors = new ArrayList<>(); //int because im too lazy to currently remove the deserializers
    // TODO: make classes void methods since the lists are stored on the class instance

    public void readMovieMultiple(){
        ApiReader apiReader = new ApiReader();
        String urlMulti = LinkConstants.MOVIE_MULTI_LINK.replace("#",API_KEY);
        urlMulti = urlMulti.replace("!","1");
        MovieMulti movieMulti = null;
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
                MovieDTO movieDTO = readMovieSingleById(String.valueOf(movieMulti.getMovieIds().get(i)));
                MovieCreditsDTO creditsDTO = apiReader.readCreditsByMovieId(String.valueOf(movieMulti.getMovieIds().get(i)));
                movieDTO.setCredits(creditsDTO);
                apiMovies.add(movieDTO);
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
                    MovieDTO movieDTO = readMovieSingleById(String.valueOf(movieMulti.getMovieIds().get(i)));
                    MovieCreditsDTO creditsDTO = apiReader.readCreditsByMovieId(String.valueOf(movieMulti.getMovieIds().get(i)));
                    movieDTO.setCredits(creditsDTO);
                    apiMovies.add(movieDTO);
                    apiCredits.add(apiReader.readCreditsByMovieId(String.valueOf(movieMulti.getMovieIds().get(i))));
                }
            }
        System.out.println("Finished fetching all " + totalPages + " pages");
        }

        //TODO: create ActorDTOs and DirectorDTOs with checks, using yet another method down here
        for (MovieCreditsDTO credit : apiCredits) {
            credit.getCast().forEach(actorDTO -> apiActors.add(actorDTO));
            credit.getCrew().forEach(directorDTO -> apiDirectors.add(directorDTO));
            //once the serializer is detached we can do a check on credit.getCrew().getDepartment(); and then get the ones that says "Directing" or somethin
            // TODO: add these onto movies, they are currently in a huge list that isnt sorted with no connection to a movie
            // TODO: follow up from above, this can be avoided IF we make a lot of requests to get results for a single actor at the time
        }
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

    public MovieCreditsDTO readCreditsByMovieId(String id) { //incomplete method
        ApiReader apiReader = new ApiReader();
        MovieCreditsDTO credits = null;
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
                credits = apiReader.jsonToCastDtoSingle(body);
//                System.out.println(cast);
            } else {
                System.out.println("People: GET request failed. Status code: " + response1.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return credits;
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

    private MovieCreditsDTO jsonToCastDtoSingle(String jsonString){
        try {
            return om.readValue(jsonString, MovieCreditsDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

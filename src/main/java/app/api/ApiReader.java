package app.api;

import app.entities.dtos.MovieDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiReader {

    ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new JavaTimeModule());
    public void read(String url) {
        ApiReader reader = new ApiReader();
        MovieDTO movie = null;
        try {
            // Create an HttpClient instance
            HttpClient client = HttpClient.newHttpClient();

            // Create a request
            HttpRequest request1 = HttpRequest.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .uri(new URI(url))
                    .GET()
                    .build();

            // Send the request and get the response
            HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

            // Check the status code and print the response
            if (response1.statusCode() == 200) {
                String body = response1.body();
                System.out.println(body);
                System.out.println("--------------------");
                movie = reader.jsonToDto(body);
                System.out.println(movie);
            } else {
                System.out.println("GET request failed. Status code: " + response1.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MovieDTO jsonToDto(String jsonString){
        try {
//            return om.readValue(jsonString, MovieDTO[].class)[0]; //use this if you get an array with one object
            return om.readValue(jsonString, MovieDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

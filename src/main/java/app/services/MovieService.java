package app.services;

import app.entities.dtos.MovieDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MovieService {

    private static final String MOVIE_API_URL = "https://api.themoviedb.org/3/movie/%d?api_key=%s";
    private final String apiKey;
    private final HttpClient client;
    private final ObjectMapper om;

    public MovieService(String apiKey) {
        this.apiKey = apiKey;
        this.client = HttpClient.newHttpClient();
        this.om = new ObjectMapper();
        this.om.registerModule(new JavaTimeModule());
        this.om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public MovieDTO getMovieById(int id) {
        String url = String.format(MOVIE_API_URL, id, apiKey);

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return om.readValue(response.body(), MovieDTO.class);
            } else {
                System.out.println("GET request failed: " + response.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}

package app.constants;

public final class LinkConstants {
    private LinkConstants() {}

    public static final String MOVIE_SINGLE_LINK = "https://api.themoviedb.org/3/movie/!?api_key=#";
    public static final String MOVIE_MULTI_LINK = "https://api.themoviedb.org/3/discover/movie?api_key=#&with_original_language=da&region=DK&page=!";
    public static final String MOVIE_CAST_LINK = "https://api.themoviedb.org/3/movie/!/credits?api_key=#";
    public static final String PERSON_LINK = "https://api.themoviedb.org/3/person/!?api_key=#";
}

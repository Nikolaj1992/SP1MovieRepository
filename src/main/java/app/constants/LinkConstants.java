package app.constants;

public final class LinkConstants {
    private LinkConstants() {}

    // # = api_key
    // ! = id of person/movie
    // ! = page number (only MOVIE_MULTI_LINK)

    public static final String MOVIE_SINGLE_LINK = "https://api.themoviedb.org/3/movie/!?api_key=#";
//    public static final String MOVIE_MULTI_LINK = "https://api.themoviedb.org/3/discover/movie?api_key=#&with_original_language=da&region=DK&page=!"; //without 5 year limit
    public static final String MOVIE_MULTI_LINK = "https://api.themoviedb.org/3/discover/movie?api_key=#&with_original_language=da&region=DK&page=!&primary_release_date.gte=2019-01-01&primary_release_date.lte=2024-12-31";
    public static final String MOVIE_CAST_LINK = "https://api.themoviedb.org/3/movie/!/credits?api_key=#";
    public static final String PERSON_LINK = "https://api.themoviedb.org/3/person/!?api_key=#";
}

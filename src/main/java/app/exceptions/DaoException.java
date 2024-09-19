package app.exceptions;

import app.entities.Movie;
import app.entities.dtos.MovieDTO;

public class DaoException extends RuntimeException {

    // TODO improve these exceptions as project progresses

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }

    // Nested static classes, without access to one another
    public static class MovieNotFoundException extends DaoException {
        public MovieNotFoundException(Integer id) {
            super("Movie with ID " + id + " not found");
        }
    }

    public static class MovieDeleteException extends DaoException {
        public MovieDeleteException(Integer id, Throwable cause) {
            super("Failed to delete movie with ID " + id, cause);
        }
    }

    public static class MovieUpdateException extends DaoException {
        public MovieUpdateException(Integer id, Throwable cause) {
            super("Failed to update movie with ID " + id, cause);
        }
    }

    public static class MovieCreateException extends DaoException {
        public MovieCreateException(MovieDTO movieDTO, Throwable cause) {
            super("Failed to create movie entity " + movieDTO, cause);
        }
    }

    public static class MovieFindAllException extends DaoException {
        public MovieFindAllException(Throwable cause) {
            super("Failed to find all movies from database", cause);
        }
    }

}

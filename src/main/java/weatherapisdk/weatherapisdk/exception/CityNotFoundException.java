package weatherapisdk.weatherapisdk.exception;

/**
 * Exception thrown when the requested city is not found.
 */
public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(String message) {
        super(message);
    }
}
package weatherapisdk.weatherapisdk.exception;

/**
 * Exception thrown when an invalid API key is provided.
 */
public class InvalidAPIKeyException extends RuntimeException {
    public InvalidAPIKeyException(String message) {
        super(message);
    }
}

package weatherapisdk.weatherapisdk.exception;

/**
 * Exception thrown when a network-related error occurs.
 */
public class NetworkException extends RuntimeException {
    public NetworkException(String message, Throwable cause) {
        super(message, cause);
    }
}

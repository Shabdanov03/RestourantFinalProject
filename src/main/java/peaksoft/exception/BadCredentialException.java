package peaksoft.exception;

/**
 * Shabdanov Ilim
 **/
public class BadCredentialException extends RuntimeException{
    public BadCredentialException() {
    }

    public BadCredentialException(String message) {
        super(message);
    }
}

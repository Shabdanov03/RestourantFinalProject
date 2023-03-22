package peaksoft.exception;

/**
 * Shabdanov Ilim
 **/
public class BadRequestException extends RuntimeException{
    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }
}

package peaksoft.exception;

/**
 * Shabdanov Ilim
 **/
public class NotFoundException extends RuntimeException{
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }
}

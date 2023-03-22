package peaksoft.dto.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

/**
 * Shabdanov Ilim
 **/
@Builder
public record SimpleResponse(
        HttpStatus httpStatus,
        String message

) {
}

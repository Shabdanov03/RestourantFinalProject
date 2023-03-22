package peaksoft.dto.response.userResponse;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Builder
public record UserAcceptResponse(
        HttpStatus httpStatus,
        String message,
        List<UserResponseById> users
) {
}

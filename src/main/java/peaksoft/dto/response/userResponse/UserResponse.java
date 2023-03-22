package peaksoft.dto.response.userResponse;

import java.time.LocalDate;

/**
 * Shabdanov Ilim
 **/
public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth


) {
}

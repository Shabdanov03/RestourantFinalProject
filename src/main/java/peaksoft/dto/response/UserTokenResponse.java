package peaksoft.dto.response;

import lombok.Builder;
import peaksoft.enums.Role;

import java.time.LocalDate;

/**
 * Shabdanov Ilim
 **/
@Builder
public record UserTokenResponse(
        String email,
        String token

) {
}

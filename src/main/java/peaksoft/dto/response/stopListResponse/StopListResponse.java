package peaksoft.dto.response.stopListResponse;

import java.time.LocalDate;

/**
 * Shabdanov Ilim
 **/
public record StopListResponse(
        Long id,
        String reason,
        LocalDate date,
        String menuitemName
) {
}

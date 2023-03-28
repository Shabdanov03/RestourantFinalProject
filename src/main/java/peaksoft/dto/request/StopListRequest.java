package peaksoft.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

/**
 * Shabdanov Ilim
 **/
public record StopListRequest(
        String reason,
        @FutureOrPresent(message = "Date not must be in the past")
        LocalDate date,
        Long menuItemId
) {
}

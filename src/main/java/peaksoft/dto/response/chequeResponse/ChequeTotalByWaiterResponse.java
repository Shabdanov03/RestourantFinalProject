package peaksoft.dto.response.chequeResponse;

import lombok.Builder;

import java.time.LocalDate;

/**
 * Shabdanov Ilim
 **/
@Builder
public record ChequeTotalByWaiterResponse(
        LocalDate date,
        String waiterFulName,
        int chequeSum,
        int countCheque
) {
}

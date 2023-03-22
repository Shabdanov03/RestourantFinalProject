package peaksoft.dto.response.chequeResponse;

import lombok.Builder;

import java.time.LocalDate;

/**
 * Shabdanov Ilim
 **/
@Builder
public record ChequeTotalResponse(
        String restaurantName,
        LocalDate date,
        int averagePrice,
        int service,
        int grandTotal,
        int grandTotalAVG
) {
}

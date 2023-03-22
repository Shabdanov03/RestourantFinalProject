package peaksoft.dto.request;

import java.time.LocalDate;

/**
 * Shabdanov Ilim
 **/
public record ChequeRestaurantRequest(
        Long restaurantId,
        LocalDate date
) {
}

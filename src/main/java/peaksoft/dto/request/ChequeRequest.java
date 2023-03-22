package peaksoft.dto.request;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
public record ChequeRequest(
        Long userId,
        List<Long> menuItemsIdes

) {
}

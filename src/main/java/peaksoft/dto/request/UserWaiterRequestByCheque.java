package peaksoft.dto.request;


import java.time.LocalDate;

/**
 * Shabdanov Ilim
 **/
public record UserWaiterRequestByCheque(
        LocalDate date,
        Long userId
) {
}

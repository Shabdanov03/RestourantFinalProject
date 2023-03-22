package peaksoft.dto.request;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Shabdanov Ilim
 **/
public record RestaurantRequest(
        @NotBlank(message = "Restaurant name cannot be empty!")
        String name,
        String location,
        String restType,
        int service

) {
}

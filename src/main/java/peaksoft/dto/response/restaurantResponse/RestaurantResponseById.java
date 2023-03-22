package peaksoft.dto.response.restaurantResponse;

/**
 * Shabdanov Ilim
 **/
public record RestaurantResponseById(
        Long id,
        String name,
        String location,
        String restType,
        int service
) {
}

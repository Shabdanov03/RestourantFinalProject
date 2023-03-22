package peaksoft.dto.response.menuItemResponse;

import lombok.Builder;

/**
 * Shabdanov Ilim
 **/
@Builder
public record MenuItemResponseById(
        Long id,
        String name,
        String image,
        int price,
        String description,
        boolean isVegetarian,
        Long restaurantId

) {
}

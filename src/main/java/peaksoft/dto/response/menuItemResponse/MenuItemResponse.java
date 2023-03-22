package peaksoft.dto.response.menuItemResponse;

import lombok.Builder;

/**
 * Shabdanov Ilim
 **/
@Builder
public record MenuItemResponse(
        Long id,
        String name,
        String image,
        int price,
        boolean isVegetarian
) {
}

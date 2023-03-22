package peaksoft.dto.response.menuItemResponse;

import lombok.Builder;

/**
 * Shabdanov Ilim
 **/
@Builder
public record MenuItemGlobalSearchResponse(
        String categoryName,
        String subCategory,
        String name,
        int price,
        boolean isVegetarian
) {
}

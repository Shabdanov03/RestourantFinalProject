package peaksoft.dto.request;

import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Shabdanov Ilim
 **/
public record MenuItemRequest(
        @NotBlank(message = "name cannot be empty!")
        String name,
        String image,
        @Min(value = 1,message = "Price cannot be negative!")
        int price,
        String description,
        boolean isVegetarian,
        Long restaurantId,
        Long subCategoryId

) {
}

package peaksoft.dto.request;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Shabdanov Ilim
 **/
public record SubCategoryRequest(
        @NotBlank(message = "name cannot be empty!")
        String name,
        Long categoryId

) {
}

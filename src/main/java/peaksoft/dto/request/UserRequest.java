package peaksoft.dto.request;

import jakarta.validation.constraints.*;
import lombok.Builder;
import org.hibernate.validator.constraints.NotBlank;
import peaksoft.enums.Role;

import java.time.LocalDate;

/**
 * Shabdanov Ilim
 **/
@Builder
public record UserRequest(
        @NotBlank(message = "firstName cannot be empty!")
        String firstName,
        @NotBlank(message = "lastName cannot be empty!")
        String lastName,
        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,
        @NotBlank(message = "email cannot be empty!")
        @Email(message = "Invalid email!")
        String email,
        @Size(min = 5, max = 100, message = "Password must be at least 4 characters!")
        @NotBlank(message = "password cannot be empty!")
        String password,
        @NotBlank(message = "phoneNumber cannot be empty!")
        @Pattern(regexp = "\\+996\\d{9}", message = "Invalid phone number format!")
        String phoneNumber,
        @NotNull(message = "role cannot be empty!")
        Role role,
        @Positive(message = "experience cannot be empty!")
        int experience,
        @Positive(message = "restaurantId cannot be empty!")
        Long restaurantId

) {
}

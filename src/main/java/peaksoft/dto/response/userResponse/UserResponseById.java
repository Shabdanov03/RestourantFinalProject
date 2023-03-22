package peaksoft.dto.response.userResponse;

import peaksoft.enums.Role;

import java.time.LocalDate;

/**
 * Shabdanov Ilim
 **/
public record UserResponseById(
        Long id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String email,
        String password,
        String phoneNumber,
        Role role,
        int experience
) {
}

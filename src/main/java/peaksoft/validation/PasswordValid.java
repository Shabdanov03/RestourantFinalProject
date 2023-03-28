package peaksoft.validation;

/**
 * Shabdanov Ilim
 **/

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValid {
    String message() default "Password must be at least 4 characters!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
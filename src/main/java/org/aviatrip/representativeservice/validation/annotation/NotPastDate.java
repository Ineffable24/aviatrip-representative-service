package org.aviatrip.representativeservice.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import org.aviatrip.representativeservice.validation.validator.NotPastDateValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE_USE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotPastDateValidator.class)
@ReportAsSingleViolation
@Documented
public @interface NotPastDate {
    String message() default "must not be a past date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

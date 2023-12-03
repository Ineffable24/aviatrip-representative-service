package org.aviatrip.representativeservice.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import org.aviatrip.representativeservice.validation.validator.FutureTimeOffsetValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE_USE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureTimeOffsetValidator.class)
@ReportAsSingleViolation
@Documented
public @interface FutureTimeOffset {

    int hourOffset() default 24;
    String message() default "incorrect value";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


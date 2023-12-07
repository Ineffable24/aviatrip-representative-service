package org.aviatrip.representativeservice.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;
import org.aviatrip.representativeservice.enumeration.Formattable;
import org.aviatrip.representativeservice.validation.validator.FormattableEnumStringValidator;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.TYPE_USE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FormattableEnumStringValidator.class)
@ReportAsSingleViolation
@Documented
public @interface FormattableEnumString {
    Class<? extends Formattable> value();
    String propertyName() default "";
    String message() default "value doesn't exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
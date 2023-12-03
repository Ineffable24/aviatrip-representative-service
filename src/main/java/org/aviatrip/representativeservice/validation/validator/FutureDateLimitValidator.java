package org.aviatrip.representativeservice.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.aviatrip.representativeservice.validation.annotation.FutureDateLimit;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

public class FutureDateLimitValidator implements ConstraintValidator<FutureDateLimit, Temporal> {

    private int dayLimit;
    @Override
    public void initialize(FutureDateLimit constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);

        if(constraintAnnotation.dayLimit() < 1)
            throw new IllegalArgumentException("must be at least 1 day");

        dayLimit = constraintAnnotation.dayLimit();
    }
    @Override
    public boolean isValid(Temporal date, ConstraintValidatorContext constraintValidatorContext) {
        if(date == null) return true;

        long durationInDays = ChronoUnit.DAYS.between(LocalDate.now(ZoneId.of("UTC")), date);

        if(durationInDays < dayLimit)
            return true;

        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate("must not be more than " + dayLimit + " days in a future").addConstraintViolation();

        return false;
    }

}

package org.aviatrip.representativeservice.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.aviatrip.representativeservice.validation.annotation.NotPastDate;

import java.time.LocalDate;
import java.time.ZoneId;

public class NotPastDateValidator implements ConstraintValidator<NotPastDate, LocalDate> {
    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
        if(date == null) return true;

        LocalDate currentDate = LocalDate.now(ZoneId.of("UTC"));

        if(currentDate.getYear() == date.getYear())
            return currentDate.getDayOfYear() <= date.getDayOfYear();

        return currentDate.getYear() < date.getYear();
    }

}

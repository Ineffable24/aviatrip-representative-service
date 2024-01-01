package org.aviatrip.representativeservice.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.aviatrip.representativeservice.validation.annotation.NotPastDate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;

public class NotPastDateValidator implements ConstraintValidator<NotPastDate, Temporal> {
    @Override
    public boolean isValid(Temporal date, ConstraintValidatorContext constraintValidatorContext) {
        if(date == null) return true;

        LocalDate currentDate = LocalDate.now(ZoneId.of("UTC"));

        if(currentDate.getYear() == date.get(ChronoField.YEAR))
            return currentDate.getDayOfYear() <= date.get(ChronoField.DAY_OF_YEAR);

        return currentDate.getYear() < date.get(ChronoField.YEAR);
    }

}

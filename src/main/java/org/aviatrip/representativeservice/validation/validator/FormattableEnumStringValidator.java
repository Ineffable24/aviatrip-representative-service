package org.aviatrip.representativeservice.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.aviatrip.representativeservice.enumeration.Formattable;
import org.aviatrip.representativeservice.validation.annotation.FormattableEnumString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FormattableEnumStringValidator implements ConstraintValidator<FormattableEnumString, String> {

    private List<String> valueList;
    private String messageTemplate;

    @Override
    public void initialize(FormattableEnumString constraintAnnotation) {
        if(!constraintAnnotation.value().isEnum())
            throw new IllegalArgumentException(constraintAnnotation.value().getSimpleName() + " is not enum");

        valueList = Arrays.stream(constraintAnnotation.value().getEnumConstants())
                .map(Formattable::getFormattedName)
                .collect(Collectors.toList());

        String propertyName = constraintAnnotation.propertyName();
        messageTemplate = propertyName + (!propertyName.isEmpty() ? " " : "") + "? doesn't exist";
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null || valueList.contains(s))
            return true;

        String msg = messageTemplate.replaceAll("\\?", s);

        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
        return false;
    }
}

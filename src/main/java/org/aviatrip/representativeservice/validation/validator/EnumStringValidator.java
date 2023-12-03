package org.aviatrip.representativeservice.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.aviatrip.representativeservice.validation.annotation.EnumString;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumStringValidator implements ConstraintValidator<EnumString, String> {

    private Set<String> valueList;
    private String messageTemplate;

    @Override
    public void initialize(EnumString constraintAnnotation) {
        valueList = Arrays.stream(constraintAnnotation.enumClazz()
                .getEnumConstants())
                .map(Enum::toString)
                .collect(Collectors.toSet());

        String propertyName = constraintAnnotation.propertyName();
        messageTemplate = (!propertyName.isEmpty() ? propertyName + " " : "") + "? doesn't exist";
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null || valueList.contains(s.toUpperCase()))
            return true;

        String msg = messageTemplate.replaceAll("\\?", s);

        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(msg).addConstraintViolation();
        return false;
    }
}
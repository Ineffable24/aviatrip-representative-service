package org.aviatrip.representativeservice.exceptionhandler;


import lombok.extern.slf4j.Slf4j;
import org.aviatrip.representativeservice.dto.response.error.ErrorResponse;
import org.aviatrip.representativeservice.dto.response.error.ErrorsResponse;
import org.aviatrip.representativeservice.dto.response.error.InternalErrorResponse;
import org.aviatrip.representativeservice.exception.BadRequestException;
import org.aviatrip.representativeservice.exception.InternalServerErrorException;
import org.aviatrip.representativeservice.exception.ResourceNotFoundException;
import org.aviatrip.representativeservice.util.LoggerMessagePreparer;
import org.aviatrip.representativeservice.util.StringPrettier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class DefaultExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsResponse handleNotValidMethodArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for(FieldError e : ex.getFieldErrors()) {
            errors.put(StringPrettier.toSnakeCase(e.getField()), e.getDefaultMessage());
        }

        ErrorsResponse errorsResponse = ErrorsResponse.builder()
                .errorMessages(errors)
                .details("invalid field values")
                .build();

        log.debug(LoggerMessagePreparer.prepareErrorMessage(ex, HttpStatus.BAD_REQUEST, errorsResponse));

        return errorsResponse;
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(BadRequestException ex) {
        log.debug(LoggerMessagePreparer.prepareDetailedErrorMessage(ex, HttpStatus.BAD_REQUEST));

        return ex.getErrorResponse().orElse(
                ErrorResponse.builder()
                        .errorMessage("bad request")
                        .details("please try later")
                        .build()
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.debug(LoggerMessagePreparer.prepareDetailedErrorMessage(ex, HttpStatus.NOT_FOUND));

        return ex.getErrorResponse().orElse(
                ErrorResponse.builder()
                        .errorMessage("resource not found")
                        .details("please try later")
                        .build()
        );
    }

    @ExceptionHandler(InternalServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerErrorException(InternalServerErrorException ex) {
        log.error(LoggerMessagePreparer.prepareDetailedErrorMessage(ex, HttpStatus.INTERNAL_SERVER_ERROR));

        return InternalErrorResponse.DEFAULT;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error(LoggerMessagePreparer.prepareErrorMessage(ex, HttpStatus.INTERNAL_SERVER_ERROR));

        return InternalErrorResponse.DEFAULT;
    }
}
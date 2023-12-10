package org.aviatrip.representativeservice.exceptionhandler;


import lombok.extern.slf4j.Slf4j;
import org.aviatrip.representativeservice.dto.response.error.ErrorResponse;
import org.aviatrip.representativeservice.dto.response.error.ErrorsResponse;
import org.aviatrip.representativeservice.dto.response.error.InternalErrorResponse;
import org.aviatrip.representativeservice.exception.BadRequestException;
import org.aviatrip.representativeservice.exception.InternalServerErrorException;
import org.aviatrip.representativeservice.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorsResponse> handleNotValidMethodArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for(FieldError e : ex.getFieldErrors()) {
            errors.put(e.getField(), e.getDefaultMessage());
        }

        ErrorsResponse errorsResponse = ErrorsResponse.builder()
                .errorMessages(errors)
                .details("invalid field values")
                .build();

        log.debug("Returning HTTP 400 Bad Request: {}", errorsResponse);

        return ResponseEntity.badRequest().body(errorsResponse);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        ErrorResponse response = ex.getErrorResponse().orElse(
                ErrorResponse.builder()
                        .errorMessage("bad request")
                        .details("please try later")
                        .build()
        );
        log.debug("Returning HTTP 400 Bad Request: {}", response);

        return ResponseEntity.badRequest()
                .body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse response = ex.getErrorResponse().orElse(
                ErrorResponse.builder()
                        .errorMessage("resource not found")
                        .details("please try later")
                        .build()
        );
        log.debug("Returning HTTP 404 Not Found: {}", response);

        return response;
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalServerErrorException(InternalServerErrorException ex) {
        ErrorResponse response = new InternalErrorResponse();

        log.error("{} thrown: {}", ex.getClass().getSimpleName(), response);

        return ResponseEntity.internalServerError()
                .body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("{} thrown: {}", ex.getClass().getSimpleName(), ex.getMostSpecificCause().getMessage());

        return ResponseEntity.internalServerError()
                .body(new InternalErrorResponse());
    }
}
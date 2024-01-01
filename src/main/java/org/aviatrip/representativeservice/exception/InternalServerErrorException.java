package org.aviatrip.representativeservice.exception;

import org.aviatrip.representativeservice.dto.response.error.ErrorResponse;

import java.util.Optional;

public class InternalServerErrorException extends RuntimeException implements DetailedException {

    ErrorResponse errorResponse;

    public InternalServerErrorException() {
    }

    public InternalServerErrorException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public Optional<ErrorResponse> getErrorResponse() {
        return Optional.ofNullable(errorResponse);
    }
}

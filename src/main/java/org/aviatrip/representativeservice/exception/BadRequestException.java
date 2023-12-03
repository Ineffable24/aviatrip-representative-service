package org.aviatrip.representativeservice.exception;

import org.aviatrip.representativeservice.dto.response.error.ErrorResponse;

import java.util.Optional;

public class BadRequestException extends RuntimeException {

    private final ErrorResponse errorResponse;

    public BadRequestException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public Optional<ErrorResponse> getErrorResponse() {
        return Optional.ofNullable(errorResponse);
    }
}

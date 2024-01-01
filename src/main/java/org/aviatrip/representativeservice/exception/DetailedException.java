package org.aviatrip.representativeservice.exception;

import org.aviatrip.representativeservice.dto.response.error.ErrorResponse;

import java.util.Optional;

public interface DetailedException {

    Optional<ErrorResponse> getErrorResponse();
}

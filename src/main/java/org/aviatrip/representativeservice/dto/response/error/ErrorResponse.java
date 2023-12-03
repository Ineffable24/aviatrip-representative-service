package org.aviatrip.representativeservice.dto.response.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse {

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("details")
    private String details;
}
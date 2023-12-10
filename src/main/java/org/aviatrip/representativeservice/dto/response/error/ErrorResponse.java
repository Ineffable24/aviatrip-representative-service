package org.aviatrip.representativeservice.dto.response.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class ErrorResponse {

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("details")
    private String details;

    public static ErrorResponse of(String errorMessage) {
        return new ErrorResponse(errorMessage, null);
    }
}
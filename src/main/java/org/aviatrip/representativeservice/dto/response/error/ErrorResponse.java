package org.aviatrip.representativeservice.dto.response.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
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
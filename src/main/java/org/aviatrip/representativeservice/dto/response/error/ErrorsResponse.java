package org.aviatrip.representativeservice.dto.response.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
public class ErrorsResponse {

    @JsonProperty("error_messages")
    private Map<String, String> errorMessages;

    @JsonProperty("details")
    private String details;
}
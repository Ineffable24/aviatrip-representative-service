package org.aviatrip.representativeservice.dto.response.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ErrorsResponse {

    @JsonProperty("error_messages")
    private Map<String, String> errorMessages;

    @JsonProperty("details")
    private String details;
}
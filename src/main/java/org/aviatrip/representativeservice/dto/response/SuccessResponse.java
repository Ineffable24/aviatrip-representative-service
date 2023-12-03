package org.aviatrip.representativeservice.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SuccessResponse {

    @JsonIgnore
    @Getter
    private final static SuccessResponse defaultSuccessResponse;

    static {
        defaultSuccessResponse = SuccessResponse.builder()
                .success("Request handled successfully")
                .details(null)
                .build();
    }

    private String success;
    private String details;
}

package org.aviatrip.representativeservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.util.List;

@Getter
public class AirplaneRequest {

    @NotNull
    @Pattern(regexp = "[\\w]{2,32}", message = "{messages.default.name}")
    private String model;

    @NotEmpty(message = "must have at least one flight seat section configured")
    private List<@NotNull @Valid AirplanePassengerSectionRequest> sections;
}

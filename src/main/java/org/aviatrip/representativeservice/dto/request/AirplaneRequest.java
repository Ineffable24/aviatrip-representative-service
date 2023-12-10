package org.aviatrip.representativeservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import org.aviatrip.representativeservice.enumeration.FlightSeatClass;
import org.aviatrip.representativeservice.validation.annotation.FormattableEnumString;

import java.util.List;

@Getter
public class AirplaneRequest {

    @NotNull
    @Pattern(regexp = "[\\w]{2,32}", message = "{messages.default.name}")
    private String model;

    @NotEmpty(message = "must have at least one flight seat section configured")
    private List<@NotNull @Valid AirplanePassengerSectionRequest> sections;

    @Getter
    public static class AirplanePassengerSectionRequest {

        @NotNull
        @FormattableEnumString(FlightSeatClass.class)
        private String seatClass;

        @Min(value = 2, message = "the minimal seat count must be {value}")
        @Max(value = 200, message = "the maximal seat count must be {value}")
        private int seatCount;

        @Min(value = 2, message = "the minimal seat row count must be {value}")
        @Max(value = 10, message = "the maximal seat row count must be {value}")
        private int seatRowCount;

        public FlightSeatClass getEnumSeatClass() {
            return FlightSeatClass.getByFormattedName(seatClass);
        }
    }
}

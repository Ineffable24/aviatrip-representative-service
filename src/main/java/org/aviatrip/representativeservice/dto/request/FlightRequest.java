package org.aviatrip.representativeservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import org.aviatrip.representativeservice.enumeration.City;
import org.aviatrip.representativeservice.enumeration.FlightSeatClass;
import org.aviatrip.representativeservice.enumeration.FlightSeatReservationDuration;
import org.aviatrip.representativeservice.validation.annotation.FormattableEnumString;
import org.aviatrip.representativeservice.validation.annotation.FutureDateLimit;
import org.aviatrip.representativeservice.validation.annotation.FutureTimeOffset;

import java.time.ZonedDateTime;
import java.util.Map;

@Getter
public class FlightRequest {

    @NotNull
    @Size(max = 32, message = "incorrect value")
    private String airplaneModel;

    @NotNull
    @FutureTimeOffset
    @FutureDateLimit
    private ZonedDateTime departureTimestamp;

    @NotNull
    private ZonedDateTime arrivalTimestamp;

    @NotNull
    @FormattableEnumString(value = City.class, propertyName = "city")
    private String source;

    @NotNull
    @FormattableEnumString(value = City.class, propertyName = "city")
    private String destination;

    @FormattableEnumString(value = FlightSeatReservationDuration.class, propertyName = "duration")
    private String reservationDuration;

    @NotEmpty(message = "must have at least one flight seat class configured")
    private Map<@NotNull @FormattableEnumString(value = FlightSeatClass.class, propertyName = "class") String,
            @Valid FlightPassengerSectionPriceRequest> sections;
    public City getSource() {
        return City.of(source);
    }

    public City getDestination() {
        return City.of(destination);
    }

    public FlightSeatReservationDuration getReservationDuration() {
        return reservationDuration == null ? FlightSeatReservationDuration.NOT_SUPPORT :
                FlightSeatReservationDuration.of(reservationDuration);
    }

    @Getter
    public static class FlightPassengerSectionPriceRequest {

        @Min(value = 10, message = "the minimal price must be {value}$")
        @Max(value = 20_000, message = "the maximal price must be {value}$")
        private double seatPrice;

        @Min(value = 12, message = "the minimal price must be {value}$")
        @Max(value = 22_000, message = "the maximal price must be {value}$")
        private double windowSeatPrice;
    }
}

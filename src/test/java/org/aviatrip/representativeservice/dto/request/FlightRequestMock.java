package org.aviatrip.representativeservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.With;
import org.aviatrip.representativeservice.enumeration.City;
import org.aviatrip.representativeservice.enumeration.FlightSeatReservationDuration;

import java.time.ZonedDateTime;
import java.util.Map;

@With
@AllArgsConstructor
@Builder
@Getter
public class FlightRequestMock extends FlightRequest {

    private final String airplaneModel;
    private final ZonedDateTime departureTimestamp;
    private final ZonedDateTime arrivalTimestamp;
    private final String source;
    private final String destination;
    private final String reservationDuration;

    private final Map<String, FlightRequest.FlightPassengerSectionPriceRequest> sections;

    @Override
    public City getSource() {
        return City.of(source);
    }

    @Override
    public City getDestination() {
        return City.of(destination);
    }

    @Override
    public FlightSeatReservationDuration getReservationDuration() {
        return reservationDuration == null ? FlightSeatReservationDuration.NOT_SUPPORT :
                FlightSeatReservationDuration.of(reservationDuration);
    }

    @Getter
    @AllArgsConstructor
    public static class FlightPassengerSectionPriceRequestMock extends FlightPassengerSectionPriceRequest {

        private final double seatPrice;
        private final double windowSeatPrice;
    }

    public static FlightRequestMock ofDefault() {
        return FlightRequestMock.builder()
                .airplaneModel("model1000")
                .destination("Moscow")
                .source("Ufa")
                .departureTimestamp(ZonedDateTime.now().plusHours(10))
                .arrivalTimestamp(ZonedDateTime.now().plusHours(15))
                .reservationDuration(null)
                .sections(Map.of(
                        "Economy", new FlightRequestMock.FlightPassengerSectionPriceRequestMock(100, 200),
                        "Comfort", new FlightRequestMock.FlightPassengerSectionPriceRequestMock(2000, 2200)
                ))
                .build();
    }
}

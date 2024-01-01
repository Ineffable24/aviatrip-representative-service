package org.aviatrip.representativeservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.aviatrip.representativeservice.enumeration.City;
import org.aviatrip.representativeservice.enumeration.FlightSeatReservationDuration;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
public class TicketView {

    private UUID id;
    private String position;
    @JsonProperty("window_seat")
    private boolean isWindowSeat;
    private Double price;
    private FlightTicketView flight;

    public TicketView(UUID id, boolean isWindowSeat, String position, Double price, UUID flightId, City flightSource, City flightDestination,
                      ZonedDateTime flightDepartureTimestamp, ZonedDateTime flightArrivalTimestamp, FlightSeatReservationDuration reservationDuration, String companyName, String airplaneModel) {

        this.id = id;
        this.position = position;
        this.isWindowSeat = isWindowSeat;
        this.price = price;
        this.flight = new FlightTicketView(flightId, flightSource, flightDestination, flightDepartureTimestamp, flightArrivalTimestamp,
                reservationDuration, companyName, airplaneModel);
    }
    @AllArgsConstructor
    @Getter
    public static class FlightTicketView {
        private UUID id;
        private City source;
        private City destination;
        private ZonedDateTime departureTimestamp;
        private ZonedDateTime arrivalTimestamp;
        private FlightSeatReservationDuration reservationDuration;
        private String companyName;
        private String airplaneModel;
    }
}

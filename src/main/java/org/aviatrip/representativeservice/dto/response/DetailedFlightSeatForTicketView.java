package org.aviatrip.representativeservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.aviatrip.representativeservice.enumeration.City;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface DetailedFlightSeatForTicketView extends FlightSeatForTicketView {

    UUID getFlightId();
    City getFlightSource();
    City getFlightDestination();
    ZonedDateTime getFlightDepartureTimestamp();
    ZonedDateTime getFlightArrivalTimestamp();
    @JsonProperty("company_name")
    String getFlightCompanyName();
    @JsonProperty("airplane_model")
    String getFlightAirplaneModel();
}



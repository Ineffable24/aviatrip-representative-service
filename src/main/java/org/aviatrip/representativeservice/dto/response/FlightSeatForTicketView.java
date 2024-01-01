package org.aviatrip.representativeservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.aviatrip.representativeservice.enumeration.FlightSeatClass;

import java.util.UUID;


public interface FlightSeatForTicketView {
    UUID getId();
    String getPosition();
    @JsonProperty("window_seat")
    boolean getIsWindowSeat();
    FlightSeatClass getSeatClass();
    Double getPrice();
}

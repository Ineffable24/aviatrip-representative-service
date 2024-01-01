package org.aviatrip.representativeservice.dto.response;

import org.aviatrip.representativeservice.enumeration.FlightSeatReservationDuration;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface FlightSeatReservationView {

    ZonedDateTime getFlightDepartureTimestamp();
    FlightSeatReservationDuration getFlightReservationDuration();

    UUID getFlightId();
}

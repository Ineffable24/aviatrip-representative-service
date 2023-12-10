package org.aviatrip.representativeservice.dto.response;

import org.aviatrip.representativeservice.enumeration.City;
import org.aviatrip.representativeservice.enumeration.FlightSeatReservationDuration;

import java.time.ZonedDateTime;

public record FlightView(
     ZonedDateTime departureTimestamp,
     ZonedDateTime arrivalTimestamp,
     City source,
     City destination,
     FlightSeatReservationDuration reservationDuration,
     String airplaneModel
) {}

package org.aviatrip.representativeservice.dto.response;

import org.aviatrip.representativeservice.enumeration.FlightSeatClass;

import java.util.UUID;

public record FlightSeatView(UUID id, String position, boolean isWindowSeat, double price,
                             FlightSeatClass seatClass, boolean isReserved) { }

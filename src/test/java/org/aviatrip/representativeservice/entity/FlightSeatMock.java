package org.aviatrip.representativeservice.entity;

import lombok.Builder;
import lombok.Getter;
import org.aviatrip.representativeservice.enumeration.FlightSeatClass;

import java.util.UUID;

@Getter
@Builder
public class FlightSeatMock extends FlightSeat{

    private UUID id;

    private String position;

    private boolean isWindowSeat;

    private double price;

    private FlightSeatClass seatClass;

    private Flight flight;

    private boolean isReserved;
}

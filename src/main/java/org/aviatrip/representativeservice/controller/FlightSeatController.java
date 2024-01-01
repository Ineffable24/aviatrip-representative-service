package org.aviatrip.representativeservice.controller;

import lombok.RequiredArgsConstructor;
import org.aviatrip.representativeservice.dto.response.FlightSeatForTicketView;
import org.aviatrip.representativeservice.dto.response.FlightSeatReservationView;
import org.aviatrip.representativeservice.service.FlightSeatReservationService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/flightseats")
@RequiredArgsConstructor
public class FlightSeatController {

    private final FlightSeatReservationService reservationService;

    @GetMapping("/{flightSeatId}/reservation")
    public FlightSeatReservationView getFlightSeatReservationView(@PathVariable UUID flightSeatId) {
        return reservationService.getFlightSeatReservationView(flightSeatId);
    }

    @PatchMapping("/{flightSeatId}/reservation")
    public void reserveFlightSeat(@PathVariable UUID flightSeatId) {
        reservationService.reserveFlightSeat(flightSeatId);
    }

    @GetMapping("/{flightSeatId}/purchase")
    public FlightSeatForTicketView getFlightseatForTicketView(@PathVariable UUID flightSeatId, @RequestParam boolean detailed) {
        return reservationService.getFlightSeatForTicketView(flightSeatId, detailed);
    }
}
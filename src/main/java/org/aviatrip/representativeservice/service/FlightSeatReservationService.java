package org.aviatrip.representativeservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aviatrip.representativeservice.dto.response.DetailedFlightSeatForTicketView;
import org.aviatrip.representativeservice.dto.response.FlightSeatForTicketView;
import org.aviatrip.representativeservice.dto.response.FlightSeatReservationView;
import org.aviatrip.representativeservice.dto.response.error.ErrorResponse;
import org.aviatrip.representativeservice.dto.response.error.ResourceNotFoundResponse;
import org.aviatrip.representativeservice.exception.BadRequestException;
import org.aviatrip.representativeservice.exception.ResourceNotFoundException;
import org.aviatrip.representativeservice.kafka.event.FlightSeatReservationEvent;
import org.aviatrip.representativeservice.repository.FlightSeatRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightSeatReservationService {

    private final FlightSeatRepository flightSeatRepository;

    public FlightSeatReservationView getFlightSeatReservationView(UUID flightSeatId) {
        return flightSeatRepository.findFlightSeatReservationViewByIdAndIsReserved(flightSeatId, false)
                .orElseThrow(() -> new BadRequestException(
                        ErrorResponse.builder()
                                .errorMessage("Failed to fetch flight seat reservation data")
                                .details("Flight seat not found or already reserved")
                                .build()
                ));
    }

    @Transactional
    public void reserveFlightSeat(UUID flightSeatId) {
        int affectedRowsCount = flightSeatRepository.reserveFlightIfNotReserved(flightSeatId);

        if(affectedRowsCount != 1)
            throw new BadRequestException(
                    ErrorResponse.builder()
                            .errorMessage("Failed to reserve flight seat with ID " + flightSeatId)
                            .details("Flight seat not found or already reserved")
                            .build()
            );
    }
    
    @Transactional
    public void unreserveFlightSeat(FlightSeatReservationEvent event) {
        flightSeatRepository.updateFlightSeatIsReserved(event.getFlightSeatId(), false);
    }

    public FlightSeatForTicketView getFlightSeatForTicketView(UUID flightSeatId, boolean detailed) {
        Class<? extends FlightSeatForTicketView> type = detailed ? DetailedFlightSeatForTicketView.class : FlightSeatForTicketView.class;
        return flightSeatRepository.findFlightSeatForTicketViewById(flightSeatId, type)
                .orElseThrow(() -> new ResourceNotFoundException(
                        ResourceNotFoundResponse.of("Flight seat with ID " + flightSeatId)
                ));
    }
}

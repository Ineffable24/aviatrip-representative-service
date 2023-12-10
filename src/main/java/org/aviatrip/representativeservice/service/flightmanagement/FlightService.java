package org.aviatrip.representativeservice.service.flightmanagement;

import lombok.RequiredArgsConstructor;
import org.aviatrip.representativeservice.dto.response.FlightSeatView;
import org.aviatrip.representativeservice.dto.response.FlightView;
import org.aviatrip.representativeservice.dto.response.error.ResourceNotFoundResponse;
import org.aviatrip.representativeservice.entity.Flight;
import org.aviatrip.representativeservice.entity.FlightSeat;
import org.aviatrip.representativeservice.exception.ResourceNotFoundException;
import org.aviatrip.representativeservice.repository.FlightRepository;
import org.aviatrip.representativeservice.repository.FlightSeatRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final FlightSeatRepository flightSeatRepository;

    @Transactional
    public void createFlight(Flight flight, List<FlightSeat> flightSeats) {
        flightRepository.save(flight);
        flightSeatRepository.saveAll(flightSeats);
    }

    public List<FlightView> getCompanyFlights(UUID companyId, Pageable pageable) {
        return flightRepository.findViewsByCompanyId(companyId, pageable);
    }

    public FlightView getCompanyFlight(UUID flightId, UUID companyId) {
        return flightRepository.findViewByIdAndCompanyId(flightId, companyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ResourceNotFoundResponse.of("Flight with ID " + flightId))
                );
    }

    public List<FlightSeatView> getCompanyFlightSeats(UUID flightId, UUID companyId, Pageable pageable) {
        if(!flightRepository.existsByIdAndCompanyId(flightId, companyId))
            throw new ResourceNotFoundException(ResourceNotFoundResponse.of("Flight with ID " + flightId));

        return flightSeatRepository.findViewsByFlightId(flightId, pageable);
    }
}

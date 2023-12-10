package org.aviatrip.representativeservice.repository;

import org.aviatrip.representativeservice.dto.response.FlightSeatView;
import org.aviatrip.representativeservice.entity.FlightSeat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FlightSeatRepository extends JpaRepository<FlightSeat, UUID> {
    List<FlightSeatView> findViewsByFlightId(UUID flightId, Pageable pageable);
}

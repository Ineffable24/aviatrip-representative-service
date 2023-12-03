package org.aviatrip.representativeservice.repository;

import org.aviatrip.representativeservice.entity.FlightSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlightSeatRepository extends JpaRepository<FlightSeat, UUID> {
}

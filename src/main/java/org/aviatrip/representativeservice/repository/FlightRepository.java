package org.aviatrip.representativeservice.repository;

import org.aviatrip.representativeservice.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FlightRepository extends JpaRepository<Flight, UUID> {
}

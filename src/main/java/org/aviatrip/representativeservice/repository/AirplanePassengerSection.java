package org.aviatrip.representativeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AirplanePassengerSection extends JpaRepository<AirplanePassengerSection, UUID> {
}

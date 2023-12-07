package org.aviatrip.representativeservice.repository;

import org.aviatrip.representativeservice.entity.AirplanePassengerSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AirplanePassengerSectionRepository extends JpaRepository<AirplanePassengerSection, UUID> {
}

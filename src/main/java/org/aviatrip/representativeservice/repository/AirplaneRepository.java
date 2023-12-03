package org.aviatrip.representativeservice.repository;

import org.aviatrip.representativeservice.entity.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AirplaneRepository extends JpaRepository<Airplane, UUID> {
}

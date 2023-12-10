package org.aviatrip.representativeservice.repository;

import org.aviatrip.representativeservice.dto.response.FlightView;
import org.aviatrip.representativeservice.entity.Flight;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FlightRepository extends JpaRepository<Flight, UUID> {

    Optional<FlightView> findViewByIdAndCompanyId(UUID id, UUID companyId);

    List<FlightView> findViewsByCompanyId(UUID companyId, Pageable pageable);

    boolean existsByIdAndCompanyId(UUID id, UUID companyId);
}

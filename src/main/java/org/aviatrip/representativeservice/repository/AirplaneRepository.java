package org.aviatrip.representativeservice.repository;

import org.aviatrip.representativeservice.dto.response.AirplaneView;
import org.aviatrip.representativeservice.dto.response.DetailedAirplaneView;
import org.aviatrip.representativeservice.entity.Airplane;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AirplaneRepository extends JpaRepository<Airplane, UUID> {
    boolean existsByModel(String model);

    List<AirplaneView> findAllAirplaneViewsByCompanyId(UUID companyId, Pageable pageable);

    @EntityGraph(attributePaths = "sections")
    Optional<DetailedAirplaneView> findByIdAndCompanyId(UUID id, UUID companyId);

    @EntityGraph(attributePaths = "sections")
    <A> Optional<A> findByModelAndCompanyId(String model, UUID companyId, Class<A> type);
}

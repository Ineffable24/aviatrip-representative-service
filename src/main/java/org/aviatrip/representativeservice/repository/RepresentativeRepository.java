package org.aviatrip.representativeservice.repository;


import org.aviatrip.representativeservice.entity.AviaCompanyRepresentative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RepresentativeRepository extends JpaRepository<AviaCompanyRepresentative, UUID> {

    @Modifying
    @Query(value = "DELETE FROM AviaCompanyRepresentative r where r.id = ?1")
    int deleteRepresentativeById(UUID id);
}

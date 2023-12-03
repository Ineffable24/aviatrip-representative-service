package org.aviatrip.representativeservice.repository;

import org.aviatrip.representativeservice.entity.AviaCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<AviaCompany, UUID> {

    boolean existsByIdOrNameOrHeadquartersAddress(UUID id, String name, String address);
}

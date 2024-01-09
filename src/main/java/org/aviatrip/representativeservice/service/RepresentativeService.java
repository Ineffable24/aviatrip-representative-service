package org.aviatrip.representativeservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aviatrip.representativeservice.dto.request.CompanyRequest;
import org.aviatrip.representativeservice.dto.response.error.ErrorResponse;
import org.aviatrip.representativeservice.entity.AviaCompany;
import org.aviatrip.representativeservice.entity.AviaCompanyRepresentative;
import org.aviatrip.representativeservice.exception.BadRequestException;
import org.aviatrip.representativeservice.exception.FatalKafkaException;
import org.aviatrip.representativeservice.repository.CompanyRepository;
import org.aviatrip.representativeservice.repository.RepresentativeRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RepresentativeService {

    private final RepresentativeRepository representativeRepository;
    private final CompanyRepository companyRepository;

    public void createRepresentative(UUID userId) {
        if(representativeRepository.existsById(userId))
            throw new FatalKafkaException("Representative with ID " + userId + " already exists");

        representativeRepository.save(AviaCompanyRepresentative.builder()
                .id(userId)
                .build());
    }

    public void deleteRepresentative(UUID userId) {
        int rowCount = representativeRepository.deleteRepresentativeById(userId);
        if(rowCount == 0)
            throw new FatalKafkaException("Representative with ID " + userId + " not found");
    }
}


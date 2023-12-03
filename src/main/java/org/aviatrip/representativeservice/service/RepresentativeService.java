package org.aviatrip.representativeservice.service;

import jakarta.transaction.Transactional;
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

    @Transactional
    public void createRepresentative(UUID userId) {
        if(representativeRepository.existsById(userId))
            throw new FatalKafkaException("Representative with ID " + userId + " already exists");

        representativeRepository.save(AviaCompanyRepresentative.builder()
                .id(userId)
                .build());
    }

    @Transactional
    public void deleteRepresentative(UUID userId) {
        int rowCount = representativeRepository.deleteRepresentativeById(userId);
        if(rowCount == 0)
            throw new FatalKafkaException("Representative with ID " + userId + " not found");
    }

    @Transactional
    public AviaCompany createCompany(CompanyRequest request, UUID userId) {
        if(companyRepository.existsByIdOrNameOrHeadquartersAddress(userId, request.getName(), request.getHeadquartersAddress()))
            throw new BadRequestException(ErrorResponse.builder()
                    .errorMessage("Some of the fields are not unique")
                    .details("Try different values")
                    .build());

        return companyRepository.save(AviaCompany.builder()
                .name(request.getName())
                .headquartersAddress(request.getHeadquartersAddress())
                .representative(representativeRepository.getReferenceById(userId))
                .build());
    }
}


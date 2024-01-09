package org.aviatrip.representativeservice.service;

import lombok.RequiredArgsConstructor;
import org.aviatrip.representativeservice.dto.request.CompanyRequest;
import org.aviatrip.representativeservice.dto.response.error.ErrorResponse;
import org.aviatrip.representativeservice.entity.AviaCompany;
import org.aviatrip.representativeservice.exception.BadRequestException;
import org.aviatrip.representativeservice.repository.CompanyRepository;
import org.aviatrip.representativeservice.repository.RepresentativeRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final RepresentativeRepository representativeRepository;

    public AviaCompany createCompany(CompanyRequest request, UUID userId) {
        if(companyRepository.existsById(userId))
            throw new BadRequestException(ErrorResponse.of("You already have a company"));

        if(companyRepository.existsByNameOrHeadquartersAddress(request.getName(), request.getHeadquartersAddress()))
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

    public AviaCompany getCompanyReference(UUID userId) {
        return companyRepository.getReferenceById(userId);
    }
}

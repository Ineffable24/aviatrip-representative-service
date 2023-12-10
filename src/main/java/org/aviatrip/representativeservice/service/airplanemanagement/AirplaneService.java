package org.aviatrip.representativeservice.service.airplanemanagement;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aviatrip.representativeservice.dto.request.AirplaneRequest;
import org.aviatrip.representativeservice.dto.response.AirplaneView;
import org.aviatrip.representativeservice.dto.response.DetailedAirplaneView;
import org.aviatrip.representativeservice.dto.response.error.ResourceNotFoundResponse;
import org.aviatrip.representativeservice.entity.Airplane;
import org.aviatrip.representativeservice.exception.ResourceNotFoundException;
import org.aviatrip.representativeservice.repository.AirplaneRepository;
import org.aviatrip.representativeservice.repository.CompanyRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AirplaneService {

    private final AirplaneRepository airplaneRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public void createAirplane(Airplane airplane, UUID companyId) {
        airplane.setCompany(companyRepository.getReferenceById(companyId));

        airplaneRepository.save(airplane);
    }

    public int getTotalSeatCount(List<AirplaneRequest.AirplanePassengerSectionRequest> sections) {
        return sections.stream()
                .mapToInt(AirplaneRequest.AirplanePassengerSectionRequest::getSeatCount)
                .sum();
    }

    public List<AirplaneView> getCompanyAirplanes(UUID companyId, Pageable pageRequest) {
        return airplaneRepository.findAllAirplaneViewsByCompanyId(companyId, pageRequest);
    }

    public DetailedAirplaneView getCompanyAirplaneById(UUID airplaneId, UUID companyId) {
        return airplaneRepository.findByIdAndCompanyId(airplaneId, companyId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ResourceNotFoundResponse.of("Airplane with ID " + airplaneId))
                );
    }

    public <A> A getCompanyAirplaneByModel(String airplaneModel, UUID companyId, Class<A> tClass) {
        return airplaneRepository.findByModelAndCompanyId(airplaneModel, companyId, tClass)
                .orElseThrow(() ->
                        new ResourceNotFoundException(ResourceNotFoundResponse.of("Airplane " + airplaneModel))
                );
    }
}

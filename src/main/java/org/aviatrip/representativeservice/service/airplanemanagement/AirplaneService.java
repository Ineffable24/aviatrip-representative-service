package org.aviatrip.representativeservice.service.airplanemanagement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aviatrip.representativeservice.dto.request.AirplanePassengerSectionRequest;
import org.aviatrip.representativeservice.dto.response.AirplaneView;
import org.aviatrip.representativeservice.dto.response.DetailedAirplaneView;
import org.aviatrip.representativeservice.dto.response.error.ResourceNotFoundResponse;
import org.aviatrip.representativeservice.entity.Airplane;
import org.aviatrip.representativeservice.exception.BadRequestException;
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

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void createAirplane(Airplane airplane, UUID userId) {
        airplane.setCompany(companyRepository.getReferenceById(userId));

        airplaneRepository.save(airplane);
    }

    public int getTotalSeatCount(List<AirplanePassengerSectionRequest> sections) {
        return sections.stream()
                .mapToInt(AirplanePassengerSectionRequest::getSeatCount)
                .sum();
    }

    public List<AirplaneView> getCompanyAirplanes(UUID userId, Pageable pageRequest) {
        return airplaneRepository.findAllAirplaneViewsByCompanyId(userId, pageRequest);
    }

    public DetailedAirplaneView getCompanyAirplaneById(UUID airplaneId, UUID userId) {
        return airplaneRepository.findByIdAndCompanyId(airplaneId, userId)
                .orElseThrow(() ->
                        new BadRequestException(new ResourceNotFoundResponse("airplane with ID " + airplaneId))
                );
    }

    public DetailedAirplaneView getCompanyAirplaneByModel(String airplaneModel, UUID userId) {
        return airplaneRepository.findAirplaneViewByModelAndCompanyId(airplaneModel, userId)
                .orElseThrow(() ->
                        new BadRequestException(new ResourceNotFoundResponse("airplane " + airplaneModel))
                );
    }
}

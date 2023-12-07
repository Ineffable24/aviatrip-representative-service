package org.aviatrip.representativeservice.service.airplanemanagement;

import lombok.RequiredArgsConstructor;
import org.aviatrip.representativeservice.dto.request.AirplanePassengerSectionRequest;
import org.aviatrip.representativeservice.dto.request.AirplaneRequest;
import org.aviatrip.representativeservice.dto.response.error.ErrorResponse;
import org.aviatrip.representativeservice.dto.response.error.ValueNotUniqueResponse;
import org.aviatrip.representativeservice.exception.BadRequestException;
import org.aviatrip.representativeservice.repository.AirplaneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirplaneValidator {

    private final AirplaneRepository airplaneRepository;

    public void validate(AirplaneRequest request, int totalSeatCount) {
        assertAirplaneModelUnique(request.getModel());

        validatePassengerSections(request.getSections(), totalSeatCount);
    }

    public void assertAirplaneModelUnique(String model) {
        if(airplaneRepository.existsByModel(model))
            throw new BadRequestException(new ValueNotUniqueResponse("model"));
    }

    public void validatePassengerSections(List<AirplanePassengerSectionRequest> sections, int totalSeatCount) {
        if(totalSeatCount > 500)
            throw new BadRequestException(ErrorResponse.builder()
                    .errorMessage("Total seat count must be less than 500 seats unless you got a spaceship")
                    .build());

        long distinctClassCount = sections.stream()
                .map(AirplanePassengerSectionRequest::getEnumSeatClass)
                .distinct()
                .count();

        if(sections.size() != distinctClassCount)
            throw new BadRequestException(ErrorResponse.builder()
                    .errorMessage("Seat classes in an airplane passenger sections must be different")
                    .build());

        if(sections.stream().anyMatch(s -> s.getSeatCount() % s.getSeatRowCount() != 0))
            throw new BadRequestException(ErrorResponse.builder()
                    .errorMessage("All seat rows must have the same number of the seats within one section")
                    .build());
    }
}

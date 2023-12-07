package org.aviatrip.representativeservice.dto.mapper;

import org.aviatrip.representativeservice.dto.request.AirplaneRequest;
import org.aviatrip.representativeservice.entity.Airplane;
import org.aviatrip.representativeservice.entity.AirplanePassengerSection;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AirplaneMapper {

    public Airplane mapToEntity(AirplaneRequest request, int totalSeatCount) {

        Airplane airplane = Airplane.builder()
                .model(request.getModel())
                .capacity(totalSeatCount)
                .build();

        airplane.setSections(request.getSections()
                .stream()
                .map(req -> AirplanePassengerSection.builder()
                        .seatClass(req.getEnumSeatClass())
                        .seatCount(req.getSeatCount())
                        .seatRowCount(req.getSeatRowCount())
                        .airplane(airplane)
                        .build())
                .collect(Collectors.toSet()));

        return airplane;
    }
}

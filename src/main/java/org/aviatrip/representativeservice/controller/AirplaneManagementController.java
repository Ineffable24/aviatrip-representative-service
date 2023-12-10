package org.aviatrip.representativeservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aviatrip.representativeservice.dto.mapper.AirplaneMapper;
import org.aviatrip.representativeservice.dto.request.AirplaneRequest;
import org.aviatrip.representativeservice.dto.response.AirplaneView;
import org.aviatrip.representativeservice.dto.response.DetailedAirplaneView;
import org.aviatrip.representativeservice.entity.Airplane;
import org.aviatrip.representativeservice.service.airplanemanagement.AirplaneService;
import org.aviatrip.representativeservice.service.airplanemanagement.AirplaneValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/company/airplanes")
@RequiredArgsConstructor
public class AirplaneManagementController {

    private final AirplaneService airplaneService;
    private final AirplaneValidator airplaneValidator;
    private final AirplaneMapper airplaneMapper;

    @Value("${custom.default-page-size}")
    private int defaultPageSize;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAirplane(@RequestBody @Valid AirplaneRequest request, @RequestHeader("Subject") UUID userId) {

        int totalSeatCount = airplaneService.getTotalSeatCount(request.getSections());

        airplaneValidator.validate(request, totalSeatCount);
        Airplane airplane = airplaneMapper.mapToEntity(request, totalSeatCount);

        airplaneService.createAirplane(airplane, userId);
    }

    @GetMapping
    public List<AirplaneView> getCompanyAirplanes(@RequestParam(value = "page", defaultValue = "0") int pageNumber, @RequestHeader("subject") UUID userId) {
        Pageable pageRequest = PageRequest.of(pageNumber, defaultPageSize);

        return airplaneService.getCompanyAirplanes(userId, pageRequest);
    }

    @GetMapping("/{id}")
    public DetailedAirplaneView getCompanyAirplane(@PathVariable String id, @RequestHeader("Subject") UUID userId) {
        try {
            return airplaneService.getCompanyAirplaneById(UUID.fromString(id), userId);
        } catch (IllegalArgumentException ex) {
            return airplaneService.getCompanyAirplaneByModel(id, userId, DetailedAirplaneView.class);
        }
    }
}

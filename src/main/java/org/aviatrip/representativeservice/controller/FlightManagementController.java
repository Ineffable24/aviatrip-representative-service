package org.aviatrip.representativeservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aviatrip.representativeservice.dto.mapper.FlightMapper;
import org.aviatrip.representativeservice.dto.mapper.FlightSeatMapper;
import org.aviatrip.representativeservice.dto.request.FlightRequest;
import org.aviatrip.representativeservice.dto.response.FlightSeatView;
import org.aviatrip.representativeservice.dto.response.FlightView;
import org.aviatrip.representativeservice.entity.Airplane;
import org.aviatrip.representativeservice.entity.Flight;
import org.aviatrip.representativeservice.entity.FlightSeat;
import org.aviatrip.representativeservice.service.RepresentativeService;
import org.aviatrip.representativeservice.service.airplanemanagement.AirplaneService;
import org.aviatrip.representativeservice.service.flightmanagement.FlightService;
import org.aviatrip.representativeservice.service.flightmanagement.FlightValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/company/flights")
@RequiredArgsConstructor
public class FlightManagementController {

    private final FlightService flightService;
    private final FlightValidator flightValidator;
    private final FlightMapper flightMapper;
    private final FlightSeatMapper flightSeatMapper;
    private final AirplaneService airplaneService;
    private final RepresentativeService representativeService;

    @Value("${custom.default-page-size}")
    private int defaultPageSize;

    @PostMapping
    public void createFlight(@RequestBody @Valid FlightRequest request, @RequestHeader("Subject") UUID userId) {
        Airplane airplane = airplaneService.getCompanyAirplaneByModel(request.getAirplaneModel(), userId, Airplane.class);

        flightValidator.validate(request, airplane);

        Flight flight = flightMapper.mapToFlight(request, airplane, representativeService.getCompanyReference(userId));
        List<FlightSeat> flightSeats = flightSeatMapper.mapToFlightSeats(request.getSections(), flight);

        flightService.createFlight(flight, flightSeats);
    }

    @GetMapping
    public List<FlightView> getCompanyFlights(@RequestParam(value = "page", defaultValue = "0") int pageNumber, @RequestHeader("subject") UUID userId) {
        Pageable pageRequest = PageRequest.of(pageNumber, defaultPageSize);
        return flightService.getCompanyFlights(userId, pageRequest);
    }

    @GetMapping("/{flightId}")
    public FlightView getCompanyFlight(@PathVariable UUID flightId, @RequestHeader("Subject") UUID userId) {
        return flightService.getCompanyFlight(flightId, userId);
    }

    @GetMapping("/{flightId}/seats")
    public List<FlightSeatView> getCompanyFlightSeats(@RequestParam(value = "page", defaultValue = "0") int pageNumber,
                            @PathVariable UUID flightId, @RequestHeader("Subject") UUID userId) {

        Pageable pageRequest = PageRequest.of(pageNumber, 20);
        return flightService.getCompanyFlightSeats(flightId, userId, pageRequest);
    }
}

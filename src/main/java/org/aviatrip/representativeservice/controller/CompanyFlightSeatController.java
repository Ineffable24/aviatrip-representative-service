package org.aviatrip.representativeservice.controller;

import lombok.RequiredArgsConstructor;
import org.aviatrip.representativeservice.dto.response.FlightSeatView;
import org.aviatrip.representativeservice.service.flightmanagement.FlightService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/company/flightseats")
@RequiredArgsConstructor
public class CompanyFlightSeatController {

    private final FlightService flightService;

    @GetMapping
    public List<FlightSeatView> getCompanyFlightSeats(@RequestParam(value = "page", defaultValue = "0") int pageNumber,
                                                      @RequestParam("flight_id") UUID flightId, @RequestHeader("Subject") UUID userId) {

        Pageable pageRequest = PageRequest.of(pageNumber, 20);
        return flightService.getCompanyFlightSeats(flightId, userId, pageRequest);
    }
}

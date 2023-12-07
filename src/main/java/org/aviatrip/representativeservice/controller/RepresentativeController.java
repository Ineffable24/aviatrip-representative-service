package org.aviatrip.representativeservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.aviatrip.representativeservice.dto.request.CompanyRequest;
import org.aviatrip.representativeservice.dto.response.SuccessResponse;
import org.aviatrip.representativeservice.entity.AviaCompany;
import org.aviatrip.representativeservice.kafka.event.RepresentativeEvent;
import org.aviatrip.representativeservice.kafka.producer.RepresentativeEventProducer;
import org.aviatrip.representativeservice.service.RepresentativeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.aviatrip.representativeservice.enumeration.RepresentativeEventType.VERIFIED;

@RestController
@RequestMapping("/representative/company")
@RequiredArgsConstructor
public class RepresentativeController {

    private final RepresentativeService representativeService;
    private final RepresentativeEventProducer representativeEventProducer;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse createCompany(@RequestHeader("subject") UUID userId, @RequestBody @Valid CompanyRequest request) {
        AviaCompany company = representativeService.createCompany(request, userId);

        representativeEventProducer.sendRepresentativeEvent(new RepresentativeEvent(company.getId(), VERIFIED));

        return SuccessResponse.builder()
                .success("Company registered successfully")
                .details("Token should be reissued to be able to manage your company")
                .build();
    }
}

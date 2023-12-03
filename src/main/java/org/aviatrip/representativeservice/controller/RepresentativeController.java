package org.aviatrip.representativeservice.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@RequestMapping("/representative")
@RequiredArgsConstructor
public class RepresentativeController {

    private final RepresentativeService representativeService;
    private final RepresentativeEventProducer representativeEventProducer;

    @PostMapping("/company")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessResponse createCompany(@RequestHeader("subject") UUID userId, @RequestBody @Valid CompanyRequest request) {
        AviaCompany company = representativeService.createCompany(request, userId);

        representativeEventProducer.sendRepresentativeEvent(new RepresentativeEvent(company.getId(), VERIFIED));

        return SuccessResponse.builder()
                .success("Company registered successfully")
                .details("Token should be reissued to be able to manage your company")
                .build();
    }

    @GetMapping
    public Object assertVerified(@RequestHeader("subject") UUID userId, @RequestHeader("role") final String role) {
        return new Object() {
            @JsonProperty("role")
            private final String roleValue = role;

            public String getRoleValue() {
                return roleValue;
            }
        };
    }
}

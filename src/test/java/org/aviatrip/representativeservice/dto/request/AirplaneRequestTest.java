package org.aviatrip.representativeservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class AirplaneRequestTest {
    private String model;
    private List<AirplanePassengerSectionRequestTest> sections;
}
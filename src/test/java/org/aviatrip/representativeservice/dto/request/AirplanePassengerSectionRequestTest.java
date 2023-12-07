package org.aviatrip.representativeservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AirplanePassengerSectionRequestTest {

    @JsonProperty("seat_class")
    private String seatClass;
    @JsonProperty("seat_count")
    private int seatCount;
    @JsonProperty("seat_row_count")
    private int seatRowCount;
}
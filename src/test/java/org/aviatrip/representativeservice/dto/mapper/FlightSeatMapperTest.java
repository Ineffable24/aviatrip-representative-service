package org.aviatrip.representativeservice.dto.mapper;

import org.aviatrip.representativeservice.dto.request.FlightRequest;
import org.aviatrip.representativeservice.dto.request.FlightRequestMock.FlightPassengerSectionPriceRequestMock;
import org.aviatrip.representativeservice.entity.*;
import org.aviatrip.representativeservice.enumeration.FlightSeatClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FlightSeatMapperTest {

    static FlightSeatMapper mapper;

    @BeforeAll
    static void setUp() {
        mapper = new FlightSeatMapper();
    }

    @Test
    void givenFlightWhenValidThenCreateFlightSeats() {
        Map<String, FlightRequest.FlightPassengerSectionPriceRequest> priceDataList =
                Map.of(
                        "Business", new FlightPassengerSectionPriceRequestMock(1000, 1200),
                        "Comfort", new FlightPassengerSectionPriceRequestMock(500, 550)
                );

        Airplane airplane = Airplane.builder()
                .capacity(30)
                .sections(Set.of(
                        AirplanePassengerSection.builder()
                                .seatClass(FlightSeatClass.BUSINESS)
                                .seatCount(10)
                                .seatRowCount(5)
                                .build(),
                        AirplanePassengerSection.builder()
                                .seatClass(FlightSeatClass.COMFORT)
                                .seatCount(20)
                                .seatRowCount(5)
                                .build()
                ))
                .build();

        Flight flight = Flight.builder().airplane(airplane).build();

        List<FlightSeat> seats = assertDoesNotThrow(() -> mapper.mapToFlightSeats(priceDataList, flight));

        assertThat(seats.size()).isEqualTo(airplane.getCapacity());

        assertTrue(seats.contains(FlightSeatMock.builder()
                        .position("B3")
                        .price(1200)
                        .isWindowSeat(true)
                        .seatClass(FlightSeatClass.BUSINESS)
                        .build()
                )
        );
    }
}
package org.aviatrip.representativeservice.service.flightmanagement;

import org.aviatrip.representativeservice.dto.request.AirplaneUtil;
import org.aviatrip.representativeservice.dto.request.FlightRequest;
import org.aviatrip.representativeservice.dto.request.FlightRequestMock;
import org.aviatrip.representativeservice.entity.Airplane;
import org.aviatrip.representativeservice.exception.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FlightValidatorTest {

    private FlightValidator flightValidator;

    @BeforeEach
    void runUp() {
        flightValidator = new FlightValidator();
    }

    @Test
    void whenValidFlightRequestThenReturnVoid() {
        FlightRequest request = FlightRequestMock.ofDefault();
        Airplane airplane = AirplaneUtil.ofDefault();

        assertDoesNotThrow(() -> flightValidator.validate(request, airplane));
    }

    @Test
    void givenFlightRequestWhenFlightDuration100hoursThenThrowException() {

        FlightRequest request = FlightRequestMock.ofDefault().withArrivalTimestamp(ZonedDateTime.now().plusHours(115));
        Airplane airplane = AirplaneUtil.ofDefault();

        BadRequestException exception = assertThrows(BadRequestException.class, () -> flightValidator.validate(request, airplane));

        assertTrue(exception.getErrorResponse().isPresent());
        assertEquals("Flight duration must be between 30 minutes and 24 hours", exception.getErrorResponse().get().getErrorMessage());
    }

    @Test
    void givenFlightRequestWhenSourceCityEqualToDestinationCityThenThrowException() {

        FlightRequest request = FlightRequestMock.ofDefault().withDestination("Ufa");
        Airplane airplane = AirplaneUtil.ofDefault();

        BadRequestException exception = assertThrows(BadRequestException.class, () -> flightValidator.validate(request, airplane));

        assertTrue(exception.getErrorResponse().isPresent());
        assertEquals("Source city must not be equal to the destination city", exception.getErrorResponse().get().getErrorMessage());
    }

    @AfterEach
    void tearDown() {
        flightValidator = null;
    }
}
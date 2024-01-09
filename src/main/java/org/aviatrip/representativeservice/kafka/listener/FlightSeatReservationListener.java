package org.aviatrip.representativeservice.kafka.listener;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aviatrip.representativeservice.enumeration.FlightSeatReservationEventType;
import org.aviatrip.representativeservice.kafka.event.FlightSeatReservationEvent;
import org.aviatrip.representativeservice.service.FlightSeatReservationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
@Validated
@Slf4j
public class FlightSeatReservationListener {

    private final FlightSeatReservationService reservationService;

    @KafkaListener(topics = "${spring.kafka.custom.consumer.flightseat-reservation.topic}",
            groupId = "${spring.kafka.custom.consumer.flightseat-reservation.topic}",
            containerFactory = "mainListenerContainerFactory",
            properties = "spring.json.value.default.type=org.aviatrip.representativeservice.kafka.event.FlightSeatReservationEvent")
    public void listenFlightSeatReservationEvent(@Payload @Valid FlightSeatReservationEvent event) {
        if(event.getEventType().equals(FlightSeatReservationEventType.EXPIRED))
            reservationService.unreserveFlightSeat(event);
    }
}

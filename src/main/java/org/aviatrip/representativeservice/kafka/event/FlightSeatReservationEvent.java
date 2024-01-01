package org.aviatrip.representativeservice.kafka.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import org.aviatrip.representativeservice.enumeration.FlightSeatReservationEventType;
import org.aviatrip.representativeservice.validation.annotation.EnumString;

import java.util.UUID;

@Builder
@Getter
public class FlightSeatReservationEvent {

    @JsonProperty("flight_seat_id")
    @NotNull
    private UUID flightSeatId;

    @JsonProperty("event_type")
    @NotNull
    @EnumString(FlightSeatReservationEventType.class)
    private String eventType;

    @JsonIgnore
    public FlightSeatReservationEventType getEventType() {
        return FlightSeatReservationEventType.valueOf(eventType.toUpperCase());
    }
}

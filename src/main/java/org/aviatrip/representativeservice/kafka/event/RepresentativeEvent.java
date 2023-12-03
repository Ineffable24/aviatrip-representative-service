package org.aviatrip.representativeservice.kafka.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.aviatrip.representativeservice.enumeration.RepresentativeEventType;

import java.util.UUID;

public record RepresentativeEvent(@JsonProperty("user_id") UUID userId,
            @JsonProperty("event_type") RepresentativeEventType eventType) {
}

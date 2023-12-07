package org.aviatrip.representativeservice.kafka.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.aviatrip.representativeservice.enumeration.RepresentativeUserEventType;
import org.aviatrip.representativeservice.validation.annotation.EnumString;

import java.util.UUID;



@Getter
@Setter
@ToString
public class RepresentativeUserEvent {

    @JsonProperty("user_id")
    @NotNull
    private UUID userId;

    @JsonProperty("event_type")
    @NotNull
    @EnumString(RepresentativeUserEventType.class)
    private String eventType;

    @JsonIgnore
    public RepresentativeUserEventType getEventTypeEnum() {
        return RepresentativeUserEventType.valueOf(eventType.toUpperCase());
    }
}

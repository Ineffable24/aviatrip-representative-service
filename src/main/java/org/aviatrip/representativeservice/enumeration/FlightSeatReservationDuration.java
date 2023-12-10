package org.aviatrip.representativeservice.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public enum FlightSeatReservationDuration implements Formattable {

    NOT_SUPPORT(0, "not support"),
    SIX_HOURS(21_600, "6 hours"),
    TWELVE_HOURS(43_200, "12 hours"),
    TWENTY_FOUR_HOURS(86_400, "24 hours"),
    THIRTY_SIX_HOURS(129_600, "32 hours"),
    FORTY_EIGHT_HOURS(172_800, "48 hours");

    public static final Map<String, FlightSeatReservationDuration> map = new HashMap<>();

    static {
        for(var duration : FlightSeatReservationDuration.values()) {
            map.put(duration.getFormattedName(), duration);
        }
    }

    @Getter
    private final int seconds;
    private final String name;

    @Override
    @JsonValue
    public String getFormattedName() {
        return name;
    }

    public static FlightSeatReservationDuration of(String name) {
        return Optional.ofNullable(map.get(name))
                .orElseThrow(() -> new NoSuchElementException("no such duration"));
    }
}

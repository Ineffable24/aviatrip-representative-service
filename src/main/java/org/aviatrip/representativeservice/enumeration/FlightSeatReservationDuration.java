package org.aviatrip.representativeservice.enumeration;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public enum FlightSeatReservationDuration {

    SIX_HOURS(21_600, "6 hours"),
    TWELVE_HOURS(43_200, "12 hours"),
    TWENTY_FOUR_HOURS(86_400, "24 hours"),
    THIRTY_SIX_HOURS(129_600, "32 hours"),
    FORTY_EIGHT_HOURS(172_800, "48 hours");

    public static final Map<Long, FlightSeatReservationDuration> map;

    static  {
        map = new HashMap<>();

        for(var duration : FlightSeatReservationDuration.values()) {
            map.put(duration.getSeconds(), duration);
        }
    }

    private final long seconds;
    private final String name;

    FlightSeatReservationDuration(long seconds, String name) {
        this.seconds = seconds;
        this.name = name;
    }

    public long getSeconds() {
        return seconds;
    }

    public String getName() {
        return name;
    }

    public static boolean existsByValue(long seconds) {
        return map.containsKey(seconds);
    }

    public static FlightSeatReservationDuration valueOf(long seconds) {
        if(map.containsKey(seconds))
            throw new NoSuchElementException("no such duration");

        var duration = map.get(seconds);

        if(duration == null)
            throw new IllegalArgumentException(String.valueOf(seconds));

        return duration;
    }
}

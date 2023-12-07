package org.aviatrip.representativeservice.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.NoSuchElementException;

public enum FlightSeatClass implements Formattable{
    ECONOMY("Economy"),
    COMFORT("Comfort"),
    BUSINESS("Business"),
    FIRST_CLASS("First class");

    private final String name;

    FlightSeatClass(String name) {
        this.name = name;
    }

    @Override
    @JsonValue
    public String getFormattedName() {
        return name;
    }

    public static FlightSeatClass getByFormattedName(String string) {
        for(var seatClass : FlightSeatClass.values()) {
            if(seatClass.getFormattedName().equals(string))
                return seatClass;
        }
        throw new NoSuchElementException();
    }
}

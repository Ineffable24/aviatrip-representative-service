package org.aviatrip.representativeservice.enumeration;

public enum FlightSeatClass {
    ECONOMY("Economy"),
    COMFORT("Comfort"),
    BUSINESS("Business"),
    FIRST_CLASS("First class");

    private final String name;

    FlightSeatClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

package org.aviatrip.representativeservice.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum City {
    MOSCOW("Moscow"),
    VLADIVOSTOK("Vladivostok"),
    SAMARA("Samara"),
    NOVOSIBIRSK("Novosibirsk"),
    UFA("Ufa"),
    OMSK("Omsk"),
    KRASNODAR("Krasnodar"),
    VORONEZH("Voronezh"),
    PERM("Perm"),
    KEMEROVO("Kemerovo"),
    ROSTOV("Rostov");

    private final String cityName;

    City(String cityName) {
        this.cityName = cityName;
    }

    @JsonValue
    public String getCityName() {
        return cityName;
    }
}



package org.aviatrip.representativeservice.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public enum City implements Formattable {
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

    private static final Map<String, City> map = new HashMap<>(City.values().length);

    static {
        for(City city : City.values()) {
            map.put(city.getFormattedName(), city);
        }
    }

    private final String cityName;

    City(String cityName) {
        this.cityName = cityName;
    }

    @Override
    @JsonValue
    public String getFormattedName() {
        return cityName;
    }


    public static City of(String string) {
        return Optional.ofNullable(map.get(string))
                .orElseThrow(() -> new NoSuchElementException("no such city"));
    }
}



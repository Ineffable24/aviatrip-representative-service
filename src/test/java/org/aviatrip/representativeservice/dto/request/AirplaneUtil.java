package org.aviatrip.representativeservice.dto.request;

import org.aviatrip.representativeservice.entity.Airplane;
import org.aviatrip.representativeservice.entity.AirplanePassengerSection;
import org.aviatrip.representativeservice.enumeration.FlightSeatClass;

import java.util.Set;

public class AirplaneUtil {

    public static Airplane ofDefault() {
        return Airplane
                .builder()
                .model("model1000")
                .capacity(32)
                .sections(Set.of(
                        AirplanePassengerSection.builder()
                                .seatClass(FlightSeatClass.ECONOMY)
                                .seatCount(20)
                                .seatRowCount(4)
                                .build(),
                        AirplanePassengerSection.builder()
                                .seatClass(FlightSeatClass.COMFORT)
                                .seatCount(12)
                                .seatRowCount(4)
                                .build()
                ))
                .build();
    }
}

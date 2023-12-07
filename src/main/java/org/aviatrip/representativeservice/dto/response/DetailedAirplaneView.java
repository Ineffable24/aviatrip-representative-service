package org.aviatrip.representativeservice.dto.response;


import org.aviatrip.representativeservice.enumeration.FlightSeatClass;

import java.util.Set;
import java.util.UUID;



public interface DetailedAirplaneView {

    UUID getId();
    String getModel();
    int getCapacity();

    Set<AirplanePassengerSectionView> getSections();

    interface AirplanePassengerSectionView {

        FlightSeatClass getSeatClass();
        int getSeatCount();
        int getSeatRowCount();
    }
}

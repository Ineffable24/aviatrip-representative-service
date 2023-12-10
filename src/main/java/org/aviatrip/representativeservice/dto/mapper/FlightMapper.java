package org.aviatrip.representativeservice.dto.mapper;

import org.aviatrip.representativeservice.dto.request.FlightRequest;
import org.aviatrip.representativeservice.entity.Airplane;
import org.aviatrip.representativeservice.entity.AviaCompany;
import org.aviatrip.representativeservice.entity.Flight;
import org.springframework.stereotype.Service;

@Service
public class FlightMapper {

    public Flight mapToFlight(FlightRequest request, Airplane airplane, AviaCompany company) {
        return Flight.builder()
                .departureTimestamp(request.getDepartureTimestamp())
                .arrivalTimestamp(request.getArrivalTimestamp())
                .source(request.getSource())
                .destination(request.getDestination())
                .reservationDuration(request.getReservationDuration())
                .airplane(airplane)
                .company(company)
                .build();
    }
}

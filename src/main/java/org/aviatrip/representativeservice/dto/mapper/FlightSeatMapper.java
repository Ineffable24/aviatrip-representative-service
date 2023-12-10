package org.aviatrip.representativeservice.dto.mapper;

import org.aviatrip.representativeservice.dto.request.FlightRequest;
import org.aviatrip.representativeservice.entity.AirplanePassengerSection;
import org.aviatrip.representativeservice.entity.Flight;
import org.aviatrip.representativeservice.entity.FlightSeat;
import org.aviatrip.representativeservice.enumeration.FlightSeatClass;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class FlightSeatMapper {

    public List<FlightSeat> mapToFlightSeats(Map<String, FlightRequest.FlightPassengerSectionPriceRequest> priceDataList, Flight flight) {
        var airplane = flight.getAirplane();
        List<FlightSeat> seats = new ArrayList<>(airplane.getCapacity());

        for(var section : airplane.getSections()) {
            var priceData = priceDataList.get(section.getSeatClass().getFormattedName());
            fillListWithFlightSeat(seats, section, priceData, section.getSeatClass(), flight);
        }
        return seats;
    }

    private void fillListWithFlightSeat(List<FlightSeat> list, AirplanePassengerSection section,
                                       FlightRequest.FlightPassengerSectionPriceRequest priceData, FlightSeatClass seatClass, Flight flight) {

        int rowSeatCount = section.getSeatCount() / section.getSeatRowCount();

        for(int seatRow=1; seatRow <= section.getSeatRowCount(); seatRow++) {
            for(int rowSeatPosition=1; rowSeatPosition <= rowSeatCount; rowSeatPosition++) {
                list.add(createFlightSeat(seatRow, rowSeatPosition, rowSeatCount, seatClass, priceData, flight));
            }
        }
    }

    private FlightSeat createFlightSeat(int seatRow, int rowSeatPos, int rowSeatCount, FlightSeatClass seatClass,
                                       FlightRequest.FlightPassengerSectionPriceRequest priceData, Flight flight) {

        boolean isWindowSeat = (rowSeatPos == 1) || (rowSeatPos == rowSeatCount);
        double price = isWindowSeat ? priceData.getWindowSeatPrice() : priceData.getSeatPrice();
        char seatRowPositionLetter = (char) ('A' + rowSeatPos - 1);
        String seatPosition = seatRowPositionLetter + "" + seatRow;

        return new FlightSeat(null, seatPosition, isWindowSeat, price, seatClass, flight, false);
    }
}

package org.aviatrip.representativeservice.service.flightmanagement;

import org.aviatrip.representativeservice.dto.request.FlightRequest;
import org.aviatrip.representativeservice.dto.response.error.ErrorResponse;
import org.aviatrip.representativeservice.entity.Airplane;
import org.aviatrip.representativeservice.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FlightValidator {

    public void validate(FlightRequest request, Airplane airplane) {
        Duration duration = Duration.between(request.getDepartureTimestamp(), request.getArrivalTimestamp());
        if(duration.toMinutes() < 30 || duration.toHours() > 24)
            throw new BadRequestException(ErrorResponse.of("Flight duration must be between 30 minutes and 24 hours"));

        if(request.getSource().equals(request.getDestination()))
            throw new BadRequestException(ErrorResponse.of("Source city must not be equal to the destination city"));

//        if(request.getSections().size() < airplane.getSections().size())
//            throw new BadRequestException(ErrorResponse.of("not every airplane seat class specified"));
//
        Set<String> providedSeatClasses = request.getSections().keySet();
        Set<String> airplaneSeatClasses = airplane.getSections().stream().map(s -> s.getSeatClass().getFormattedName()).collect(Collectors.toSet());

        if(!providedSeatClasses.equals(airplaneSeatClasses))
            throw new BadRequestException(ErrorResponse
                    .of("provided seat classes don't correspond to " + "airplane " + airplane.getModel() + " seat classes"));

//        for(var seatClass : seatClasses) {
//            if(!realSeatClasses.contains(FlightSeatClass.valueOf(seatClass.toUpperCase())))
//                throw new BadRequestException( + " doesn't have a " + seatClass + " seat class");
//        }
    }
}

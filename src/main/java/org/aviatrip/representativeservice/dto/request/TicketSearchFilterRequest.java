package org.aviatrip.representativeservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.aviatrip.representativeservice.enumeration.City;
import org.aviatrip.representativeservice.enumeration.FlightSeatClass;
import org.aviatrip.representativeservice.validation.annotation.FormattableEnumString;
import org.aviatrip.representativeservice.validation.annotation.FutureDateLimit;
import org.aviatrip.representativeservice.validation.annotation.NotPastDate;

import java.time.ZonedDateTime;
import java.util.Set;

@Getter
public class TicketSearchFilterRequest {

    @NotNull
    @FormattableEnumString(City.class)
    private String source;

    @NotNull
    @FormattableEnumString(City.class)
    private String destination;

    @NotNull
    @NotPastDate
    @FutureDateLimit
    private ZonedDateTime departureDate;

    @Size(min = 1, max = 5)
    private Set<String> companies;

    @FormattableEnumString(FlightSeatClass.class)
    private String seatClass = FlightSeatClass.ECONOMY.getFormattedName();

    public City getSource() {
        return City.of(source);
    }

    public City getDestination() {
        return City.of(destination);
    }

    public FlightSeatClass getSeatClass() {
        return FlightSeatClass.getByFormattedName(seatClass);
    }
}

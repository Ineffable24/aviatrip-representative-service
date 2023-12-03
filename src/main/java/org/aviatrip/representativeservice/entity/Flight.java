package org.aviatrip.representativeservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aviatrip.representativeservice.enumeration.City;
import org.aviatrip.representativeservice.enumeration.FlightSeatReservationDuration;
import org.hibernate.annotations.Immutable;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "flights")
@Immutable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight {

    @Column(name = "flight_id")
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "departure_timestamp", nullable = false)
    private ZonedDateTime departureTimestamp;

    @Column(name = "arrival_timestamp", nullable = false)
    private ZonedDateTime arrivalTimestamp;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private City source;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private City destination;

    @Column(name = "reservation_duration")
    @Enumerated(EnumType.STRING)
    private FlightSeatReservationDuration reservationDuration;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "avia_company_id", nullable = false)
    private AviaCompany company;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airplane_id", nullable = false)
    private Airplane airplane;

    @OneToMany(mappedBy = "flight")
    private final Set<FlightSeat> seats = new HashSet<>();
}
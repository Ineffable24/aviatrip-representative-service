package org.aviatrip.representativeservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.aviatrip.representativeservice.enumeration.FlightSeatClass;

import java.util.UUID;

@Entity
@Table(name = "flight_seats")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "flight")
@EqualsAndHashCode(exclude = "flight")
public class FlightSeat {

    @Column(name = "flight_seat_id")
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String position;

    @Column(name = "is_window_seat",nullable = false)
    private boolean isWindowSeat;

    @Column(nullable = false)
    private double price;

    @Column(name = "class", nullable = false)
    @Enumerated(EnumType.STRING)
    private FlightSeatClass seatClass;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @Column(name = "is_reserved")
    private boolean isReserved;
}
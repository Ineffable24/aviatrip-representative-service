package org.aviatrip.representativeservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.aviatrip.representativeservice.enumeration.FlightSeatClass;

import java.util.UUID;

@Entity
@Table(name = "airplane_seat_sections")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirplanePassengerSection {

    @Column(name = "airplane_seat_section_id")
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "seat_class", nullable = false)
    @Enumerated(EnumType.STRING)
    private FlightSeatClass seatClass;

    @Column(name = "seat_count", nullable = false)
    private int seatCount;

    @Column(name = "seat_row_count", nullable = false)
    private int rowSeatCount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airplane_id", nullable = false)
    private Airplane airplane;
}

package org.aviatrip.representativeservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.aviatrip.representativeservice.enumeration.FlightSeatClass;

import java.util.UUID;

@Entity
@Table(name = "airplane_seat_sections")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
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
    private int seatRowCount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airplane_id", nullable = false)
    private Airplane airplane;
}

package org.aviatrip.representativeservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "airplanes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "sections")
public class Airplane {

    @Column(name = "airplane_id")
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String model;

    @Column(nullable = false)
    private int capacity;

    @OneToMany(mappedBy = "airplane", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @Setter
    private Set<AirplanePassengerSection> sections;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "avia_company_id", nullable = false)
    @Setter
    private AviaCompany company;
}

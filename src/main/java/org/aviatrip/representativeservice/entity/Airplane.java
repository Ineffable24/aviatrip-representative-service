package org.aviatrip.representativeservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "airplanes")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    private Set<AirplanePassengerSection> sections;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "avia_company_id", nullable = false)
    private AviaCompany company;
}

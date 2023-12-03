package org.aviatrip.representativeservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "avia_companies")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AviaCompany {

    @Id
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "avia_company_representative_id", nullable = false, unique = true)
    @MapsId
    private AviaCompanyRepresentative representative;

    @Column(name= "headquarters_address", nullable = false, unique = true)
    private String headquartersAddress;

    @OneToMany(mappedBy = "company")
    @Setter
    private Set<Flight> flights = new HashSet<>();
}

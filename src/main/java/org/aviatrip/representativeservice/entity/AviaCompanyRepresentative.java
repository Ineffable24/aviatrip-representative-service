package org.aviatrip.representativeservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import java.util.UUID;

@Entity
@Table(name = "avia_company_representatives")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AviaCompanyRepresentative implements Persistable<UUID> {

    @Id
    @Column(name = "user_id")
    private UUID id;

    @OneToOne(mappedBy = "representative")
    private AviaCompany company;

    @Version
    private long version;

    @Override
    public boolean isNew() {
        return version == 0;
    }
}

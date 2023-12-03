package org.aviatrip.representativeservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CompanyRequest {

    @NotNull
    @Pattern(regexp = "[\\w]{2,32}", message = "{messages.company.name}")
    private String name;

    @NotNull
    @Size(min=6, max=128, message = "must be between 6 and 128 symbols")
    private String headquartersAddress;
}

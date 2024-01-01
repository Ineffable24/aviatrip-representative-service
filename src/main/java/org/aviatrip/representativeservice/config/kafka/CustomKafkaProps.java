package org.aviatrip.representativeservice.config.kafka;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Component
@ConfigurationProperties(prefix = "spring.kafka.custom")
@Setter
@Getter
@Validated
public class CustomKafkaProps {

    @NotEmpty
    private Set<String> fatalExceptions;
    @NotBlank
    private String retryTopicPrefix;
    @NotBlank
    private String dlqTopicPrefix;
}

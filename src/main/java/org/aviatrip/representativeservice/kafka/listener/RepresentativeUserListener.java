package org.aviatrip.representativeservice.kafka.listener;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aviatrip.representativeservice.enumeration.RepresentativeUserEventType;
import org.aviatrip.representativeservice.kafka.event.RepresentativeUserEvent;
import org.aviatrip.representativeservice.service.RepresentativeService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
@Slf4j
@Validated
public class RepresentativeUserListener {

    private final RepresentativeService representativeService;

    @KafkaListener(topics = "${kafka.consumer.representative-user.main-topic}",
            groupId = "${kafka.consumer.representative-user.main-groupId}",
            containerFactory = "mainRepresentativeUserConsumerContainerFactory",
            properties = "spring.json.value.default.type=org.aviatrip.representativeservice.kafka.event.RepresentativeUserEvent")
    public void handleMainRepresentativeUserEvent(@Payload @Valid RepresentativeUserEvent event) {

        log.debug(">>> Representative creation started: {}", event);
        handleRepresentativeUserEvent(event);
        log.debug("<<< Representative created: {}", event);
    }

    @KafkaListener(topics = "${kafka.consumer.representative-user.retry-topic}",
            groupId = "${kafka.consumer.representative-user.retry-groupId}",
            containerFactory = "retryRepresentativeUserConsumerContainerFactory",
            properties = "spring.json.value.default.type=org.aviatrip.representativeservice.kafka.event.RepresentativeUserEvent")
    public void handleRetryRepresentativeUserEvent(@Payload @Valid RepresentativeUserEvent event) {

        log.debug(">>> RETRY Representative creation started: {}", event);
        handleRepresentativeUserEvent(event);
        log.debug(">>> RETRY Representative created: {}", event);
    }

    public void handleRepresentativeUserEvent(RepresentativeUserEvent event) {
        RepresentativeUserEventType type = event.getEventTypeEnum();
        if(RepresentativeUserEventType.CREATED.equals(type))
            representativeService.createRepresentative(event.getUserId());
        else if(RepresentativeUserEventType.DELETED.equals(type))
            representativeService.deleteRepresentative(event.getUserId());
    }
}

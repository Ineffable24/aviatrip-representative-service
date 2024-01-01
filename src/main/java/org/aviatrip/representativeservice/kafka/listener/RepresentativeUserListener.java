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

    @KafkaListener(topics = "${spring.kafka.custom.consumer.representative-user.topic}",
            groupId = "${spring.kafka.custom.consumer.representative-user.groupId}",
            containerFactory = "mainListenerContainerFactory",
            properties = "spring.json.value.default.type=org.aviatrip.representativeservice.kafka.event.RepresentativeUserEvent")
    public void handleRepresentativeUserEvent(@Payload @Valid RepresentativeUserEvent event) {

        log.debug(">>> Representative creation started: {} <<<", event);
        dispatchRepresentativeUserEvent(event);
        log.debug(">>> Representative created: {} <<<", event);
    }

    @KafkaListener(topics = "#{'${spring.kafka.custom.retry-topic-prefix}' + '${spring.kafka.custom.consumer.representative-user.topic}'}",
            groupId = "#{'${spring.kafka.custom.retry-groupId-prefix}' + '${spring.kafka.custom.consumer.representative-user.groupId}'}",
            containerFactory = "retryListenerContainerFactory",
            properties = "spring.json.value.default.type=org.aviatrip.representativeservice.kafka.event.RepresentativeUserEvent")
    public void handleRetryRepresentativeUserEvent(@Payload @Valid RepresentativeUserEvent event) {

        log.debug(">>> RETRY Representative creation started: {} <<<", event);
        dispatchRepresentativeUserEvent(event);
        log.debug(">>> RETRY Representative created: {} <<<", event);
    }

    public void dispatchRepresentativeUserEvent(RepresentativeUserEvent event) {
        RepresentativeUserEventType type = event.getEventTypeEnum();
        if(RepresentativeUserEventType.CREATED.equals(type))
            representativeService.createRepresentative(event.getUserId());
        else if(RepresentativeUserEventType.DELETED.equals(type))
            representativeService.deleteRepresentative(event.getUserId());
    }
}

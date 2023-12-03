package org.aviatrip.representativeservice.kafka.producer;

import lombok.RequiredArgsConstructor;
import org.aviatrip.representativeservice.kafka.event.RepresentativeEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RepresentativeEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.producer.representative.topic}")
    private String representativeTopicName;

    public void sendRepresentativeEvent(RepresentativeEvent event) {
        kafkaTemplate.send(representativeTopicName, event.userId().toString(), event);
    }
}


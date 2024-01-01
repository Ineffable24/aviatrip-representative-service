package org.aviatrip.representativeservice.kafka.producer;

import org.aviatrip.representativeservice.kafka.event.RepresentativeEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RepresentativeEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String representativeTopicName;

    public RepresentativeEventProducer(KafkaTemplate<String, Object> kafkaTemplate,
                                       @Value("${spring.kafka.custom.producer.representative-topic}") String representativeTopicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.representativeTopicName = representativeTopicName;
    }

    public void sendRepresentativeEvent(RepresentativeEvent event) {
        kafkaTemplate.send(representativeTopicName, event.userId().toString(), event);
    }
}


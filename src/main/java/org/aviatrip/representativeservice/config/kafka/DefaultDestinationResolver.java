package org.aviatrip.representativeservice.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DefaultDestinationResolver extends AbstractDestinationResolver {

    public DefaultDestinationResolver(CustomKafkaProps props) {
        super(props);
    }

    @Override
    public TopicPartition apply(ConsumerRecord<?, ?> record, Exception ex) {
        Throwable cause = ex.getCause() == null ? ex : ex.getCause();
        String dlqTopic = transformMainToDlq(record.topic());

        log.error("Exception [{}] occurred sending the record to the topic [{}]", cause.getClass().getSimpleName(), dlqTopic);

        return new TopicPartition(dlqTopic, -1);
    }
}

package org.aviatrip.representativeservice.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RetryDestinationResolver extends AbstractDestinationResolver {

    public RetryDestinationResolver(CustomKafkaProps props) {
        super(props);
    }

    @Override
    public TopicPartition apply(ConsumerRecord<?, ?> record, Exception ex) {
        Throwable cause = ex.getCause() == null ? ex : ex.getCause();
        String dlqTopic = transformRetryToDlq(record.topic());

        log.error("RETRY Exception [{}] occurred sending the record to the topic [{}]", cause.getClass().getSimpleName(), dlqTopic);

        return new TopicPartition(dlqTopic, -1);
    }
}

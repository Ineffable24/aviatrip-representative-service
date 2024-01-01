package org.aviatrip.representativeservice.config.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
@Slf4j
@RequiredArgsConstructor
public class MainDestinationResolver implements BiFunction<ConsumerRecord<?,?>, Exception, TopicPartition> {

    private final CustomKafkaProps props;

    @Override
    public TopicPartition apply(ConsumerRecord<?, ?> record, Exception ex) {
        Throwable cause = ex.getCause() == null ? ex : ex.getCause();
        String destinationTopic;

        if (props.getFatalExceptions().contains(cause.getClass().getName()))
            destinationTopic = props.getDlqTopicPrefix() + record.topic();
        else
            destinationTopic = props.getRetryTopicPrefix() + record.topic();

        log.error("Exception [{}] occurred sending the record to the topic [{}]", cause.getClass().getSimpleName(), destinationTopic);

        return new TopicPartition(destinationTopic, -1);
    }
}

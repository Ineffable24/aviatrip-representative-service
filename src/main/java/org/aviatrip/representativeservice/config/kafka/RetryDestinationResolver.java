package org.aviatrip.representativeservice.config.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
@Slf4j
public class RetryDestinationResolver implements BiFunction<ConsumerRecord<?,?>, Exception, TopicPartition> {

    private final CustomKafkaProps props;

    @Override
    public TopicPartition apply(ConsumerRecord<?, ?> consumerRecord, Exception ex) {
        Throwable cause = ex.getCause() == null ? ex : ex.getCause();
        String dlqTopic = props.getDlqTopicPrefix() + consumerRecord.topic().substring(6);

        log.error("RETRY Exception [{}] occurred sending the record to the topic [{}]", cause.getClass().getSimpleName(), dlqTopic);

        return new TopicPartition(dlqTopic, -1);
    }
}

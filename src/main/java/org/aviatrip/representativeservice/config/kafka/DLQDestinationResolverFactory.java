package org.aviatrip.representativeservice.config.kafka;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.aviatrip.representativeservice.exception.FatalKafkaException;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
@Slf4j
public class DLQDestinationResolverFactory {

    public BiFunction<ConsumerRecord<?, ?>, Exception, TopicPartition> createMainTopicResolver(String retryTopic, String dlqTopic) {
        return (record, ex) -> {
            Throwable thrownEx = ex.getCause() == null ? ex : ex.getCause();
            String exName = thrownEx.getClass().getSimpleName();
            String destinationTopic = retryTopic;

            if(thrownEx instanceof FatalKafkaException)
                destinationTopic = dlqTopic;
            else if(thrownEx instanceof DeserializationException)
                destinationTopic = dlqTopic;
            else if(thrownEx instanceof MethodArgumentNotValidException)
                destinationTopic = dlqTopic;
            else if (thrownEx instanceof ConstraintViolationException)
                destinationTopic = dlqTopic;

            log.error("Exception [{}] occurred sending the record to the topic [{}]", exName, destinationTopic);

            return new TopicPartition(destinationTopic, -1);
        };
    }

    public BiFunction<ConsumerRecord<?, ?>, Exception, TopicPartition> createRetryTopicResolver(String dlqTopic) {
        return (record, ex) -> {
            Throwable thrownException = ex.getCause() == null ? ex : ex.getCause();
            log.error("RETRY Exception [{}] occurred sending the record to the topic [{}]", thrownException.getClass().getSimpleName(), dlqTopic);

            return new TopicPartition(dlqTopic, -1);
        };
    }
}

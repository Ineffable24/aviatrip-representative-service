package org.aviatrip.representativeservice.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;

import java.util.function.BiFunction;

public abstract class AbstractDestinationResolver implements BiFunction<ConsumerRecord<?,?>, Exception, TopicPartition> {

    private final CustomKafkaProps props;

    public AbstractDestinationResolver(CustomKafkaProps props) {
        this.props = props;
    }

    protected String transformMainToRetry(String src) {
        return props.getDlqTopicPrefix() + src;
    }

    protected String transformMainToDlq(String src) {
        return props.getDlqTopicPrefix() + src;
    }

    protected String transformRetryToDlq(String src) {
        String trouncedSrc = src.startsWith(props.getRetryTopicPrefix()) ? src.substring(props.getRetryTopicPrefix().length()) : src;
        return props.getDlqTopicPrefix() + trouncedSrc;
    }
}

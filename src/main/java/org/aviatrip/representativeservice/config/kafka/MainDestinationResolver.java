package org.aviatrip.representativeservice.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MainDestinationResolver extends AbstractDestinationResolver {

    private final CustomKafkaProps props;

    public MainDestinationResolver(CustomKafkaProps props) {
        super(props);
        this.props = props;
    }

    @Override
    public TopicPartition apply(ConsumerRecord<?, ?> record, Exception ex) {
        Throwable cause = ex.getCause() == null ? ex : ex.getCause();
        String destinationTopic;

        if (props.getFatalExceptions().contains(cause.getClass().getName()))
            destinationTopic = transformMainToDlq(record.topic());
        else
            destinationTopic = transformMainToRetry(record.topic());

        log.error("Exception [{}] occurred: \"{}\", sending the record to the topic [{}]",
                    cause.getClass().getSimpleName(), cause.getMessage(), destinationTopic);

        return new TopicPartition(destinationTopic, -1);
    }
}

package org.aviatrip.representativeservice.config.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.ExponentialBackOff;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(name = "kafka.consumer.representative-user.enabled", matchIfMissing = true)
public class UserCreatedKafkaConfig {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final DLQDestinationResolverFactory destinationResolverFactory;

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value("${kafka.consumer.representative-user.retry-topic}")
    private String retryTopic;

    @Value("${kafka.consumer.representative-user.dlq-topic}")
    private String dlqTopic;


    @Bean
    public ConsumerFactory<String, String> defaultConsumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> mainRepresentativeUserConsumerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();

        var recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                destinationResolverFactory.createMainTopicResolver(retryTopic, dlqTopic));

        var errorHandler = new DefaultErrorHandler(recoverer, new FixedBackOff(0L, 1L));

        concurrentKafkaListenerContainerFactory.setConsumerFactory(defaultConsumerConfig());
        concurrentKafkaListenerContainerFactory.setCommonErrorHandler(errorHandler);
        return concurrentKafkaListenerContainerFactory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> retryRepresentativeUserConsumerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(defaultConsumerConfig());

        var recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                destinationResolverFactory.createRetryTopicResolver(dlqTopic));

        var backOff = new ExponentialBackOff(1000L, 2D);
        backOff.setMaxElapsedTime(2000L);
        CommonErrorHandler errorHandler = new DefaultErrorHandler(recoverer, backOff);
        containerFactory.setCommonErrorHandler(errorHandler);

        return containerFactory;
    }
}

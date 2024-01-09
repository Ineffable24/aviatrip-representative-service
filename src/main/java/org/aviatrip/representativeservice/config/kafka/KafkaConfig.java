package org.aviatrip.representativeservice.config.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.ExponentialBackOff;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;
    private final AbstractDestinationResolver mainDestinationResolver;
    private final AbstractDestinationResolver retryDestinationResolver;
    private final AbstractDestinationResolver defaultDestinationResolver;

    @Bean
    public ConsumerFactory<String, String> defaultConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        props.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        props.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean("mainListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> mainListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();

        var recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate(),
                mainDestinationResolver);

        var errorHandler = new DefaultErrorHandler(recoverer, new FixedBackOff(0L, 0L));

        containerFactory.setConsumerFactory(defaultConsumerFactory());
        containerFactory.setCommonErrorHandler(errorHandler);
        return containerFactory;
    }

    @Bean("retryListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> retryListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(defaultConsumerFactory());

        var recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate(),
                retryDestinationResolver);

        var backOff = new ExponentialBackOff(1000L, 2D);
        backOff.setMaxElapsedTime(2000L);
        CommonErrorHandler errorHandler = new DefaultErrorHandler(recoverer, backOff);
        containerFactory.setCommonErrorHandler(errorHandler);

        return containerFactory;
    }

    @Bean("defaultListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> defaultListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(defaultConsumerFactory());

        var recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate(),
                defaultDestinationResolver);

        var backOff = new ExponentialBackOff(1000L, 3D);
        backOff.setMaxElapsedTime(5000L);
        CommonErrorHandler errorHandler = new DefaultErrorHandler(recoverer, backOff);
        containerFactory.setCommonErrorHandler(errorHandler);

        return containerFactory;
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(jsonProducerFactory());
    }

    @Bean
    public ProducerFactory<String, Object> jsonProducerFactory() {
        return new DefaultKafkaProducerFactory<>(
                Map.of(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer,
                        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class,
                        "spring.json.add.type.headers", "Boolean.FALSE")
        );
    }
}

package ru.otus.orderservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.otus.orderservice.dto.OrderEventRequest;

@Configuration
public class KafkaProducerConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return JacksonUtils.enhancedObjectMapper();
    }

    @Bean
    public ProducerFactory<String, OrderEventRequest> producerFactory(KafkaProperties kafkaProperties,
                                                                      ObjectMapper objectMapper) {
        var props = kafkaProperties.buildProducerProperties();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        var kafkaProducerFactory = new DefaultKafkaProducerFactory<String, OrderEventRequest>(props);
        kafkaProducerFactory.setValueSerializer(new JsonSerializer<>(objectMapper));
        return kafkaProducerFactory;
    }

    @Bean
    public KafkaTemplate<String, OrderEventRequest>
    kafkaTemplate(ProducerFactory<String, OrderEventRequest> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}

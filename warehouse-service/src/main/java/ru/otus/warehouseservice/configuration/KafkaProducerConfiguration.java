package ru.otus.warehouseservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.otus.warehouseservice.dto.FactoryRequest;
import ru.otus.warehouseservice.dto.OrderEventRequestAsk;
import ru.otus.warehouseservice.dto.OrderEventResponse;

@Configuration
public class KafkaProducerConfiguration {

    @Bean
    public ProducerFactory<String, OrderEventRequestAsk> orderServiceAskProducerFactory(KafkaProperties kafkaProperties,
                                                                                        ObjectMapper objectMapper) {
        var props = kafkaProperties.buildProducerProperties();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        var kafkaProducerFactory = new DefaultKafkaProducerFactory<String, OrderEventRequestAsk>(props);
        kafkaProducerFactory.setValueSerializer(new JsonSerializer<>(objectMapper));
        return kafkaProducerFactory;
    }

    @Bean
    public KafkaTemplate<String, OrderEventRequestAsk>
    kafkaToOrderServiceAskTemplate(ProducerFactory<String, OrderEventRequestAsk> orderServiceAskProducerFactory) {
        return new KafkaTemplate<>(orderServiceAskProducerFactory);
    }

    @Bean
    public ProducerFactory<String, FactoryRequest> factoryServiceProducerFactory(KafkaProperties kafkaProperties,
                                                                                 ObjectMapper objectMapper) {
        var props = kafkaProperties.buildProducerProperties();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        var kafkaProducerFactory = new DefaultKafkaProducerFactory<String, FactoryRequest>(props);
        kafkaProducerFactory.setValueSerializer(new JsonSerializer<>(objectMapper));
        return kafkaProducerFactory;
    }

    @Bean
    public KafkaTemplate<String, FactoryRequest>
    kafkaToFactoryServiceTemplate(ProducerFactory<String, FactoryRequest> orderServiceProducerFactory) {
        return new KafkaTemplate<>(orderServiceProducerFactory);
    }

    @Bean
    public ProducerFactory<String, OrderEventResponse> orderServiceResponseProducerFactory(KafkaProperties kafkaProperties,
                                                                                           ObjectMapper objectMapper) {
        var props = kafkaProperties.buildProducerProperties();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        var kafkaProducerFactory = new DefaultKafkaProducerFactory<String, OrderEventResponse>(props);
        kafkaProducerFactory.setValueSerializer(new JsonSerializer<>(objectMapper));
        return kafkaProducerFactory;
    }

    @Bean
    public KafkaTemplate<String, OrderEventResponse>
    kafkaToOrderServiceResponseTemplate(ProducerFactory<String, OrderEventResponse> orderServiceResponseProducerFactory) {
        return new KafkaTemplate<>(orderServiceResponseProducerFactory);
    }
}

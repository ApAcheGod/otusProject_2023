package ru.otus.orderservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import ru.otus.orderservice.dto.OrderEventRequestAsk;
import ru.otus.orderservice.dto.OrderEventResponse;

import static org.springframework.kafka.support.serializer.JsonDeserializer.TYPE_MAPPINGS;

@Configuration
public class KafkaConsumerConfiguration {

    @Bean
    public ConsumerFactory<String, OrderEventRequestAsk> consumerFactory(KafkaProperties kafkaProperties,
                                                                         ObjectMapper mapper) {
        var props = kafkaProperties.buildConsumerProperties();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(TYPE_MAPPINGS,
                "ru.otus.warehouseservice.dto.OrderEventRequestAsk:ru.otus.orderservice.dto.OrderEventRequestAsk");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 3);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 3_000);

        var kafkaConsumerFactory = new DefaultKafkaConsumerFactory<String, OrderEventRequestAsk>(props);
        kafkaConsumerFactory.setValueDeserializer(new JsonDeserializer<>(mapper));
        return kafkaConsumerFactory;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrderEventRequestAsk>>
    listenerContainerFactory(ConsumerFactory<String, OrderEventRequestAsk> consumerFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderEventRequestAsk>();
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true);
        factory.setConcurrency(1);
        factory.getContainerProperties().setIdleBetweenPolls(1_000);
        factory.getContainerProperties().setPollTimeout(1_000);

        var executor = new SimpleAsyncTaskExecutor("order-consumer-");
        executor.setConcurrencyLimit(10);
        var listenerTaskExecutor = new ConcurrentTaskExecutor(executor);
        factory.getContainerProperties().setListenerTaskExecutor(listenerTaskExecutor);
        return factory;
    }





    @Bean
    public ConsumerFactory<String, OrderEventResponse> consumerResponseFactory(KafkaProperties kafkaProperties,
                                                                       ObjectMapper mapper) {
        var props = kafkaProperties.buildConsumerProperties();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(TYPE_MAPPINGS,
                "ru.otus.warehouseservice.dto.OrderEventResponse:ru.otus.orderservice.dto.OrderEventResponse");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 3);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 3_000);

        var kafkaConsumerFactory = new DefaultKafkaConsumerFactory<String, OrderEventResponse>(props);
        kafkaConsumerFactory.setValueDeserializer(new JsonDeserializer<>(mapper));
        return kafkaConsumerFactory;
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, OrderEventResponse>>
    listenerContainerResponseFactory(ConsumerFactory<String, OrderEventResponse> consumerResponseFactory) {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderEventResponse>();
        factory.setConsumerFactory(consumerResponseFactory);
        factory.setBatchListener(true);
        factory.setConcurrency(1);
        factory.getContainerProperties().setIdleBetweenPolls(1_000);
        factory.getContainerProperties().setPollTimeout(1_000);

        var executor = new SimpleAsyncTaskExecutor("order-consumer-");
        executor.setConcurrencyLimit(10);
        var listenerTaskExecutor = new ConcurrentTaskExecutor(executor);
        factory.getContainerProperties().setListenerTaskExecutor(listenerTaskExecutor);
        return factory;
    }
}

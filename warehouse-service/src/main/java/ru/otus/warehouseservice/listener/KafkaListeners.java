package ru.otus.warehouseservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.otus.warehouseservice.dto.FactoryResponse;
import ru.otus.warehouseservice.dto.OrderEventRequest;
import ru.otus.warehouseservice.service.FactoryReceiveService;
import ru.otus.warehouseservice.service.impl.OrderServiceAskNotificationKafkaService;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaListeners {

    private final FactoryReceiveService factoryReceiveService;

    private final OrderServiceAskNotificationKafkaService orderServiceAskNotificationKafkaService;

    @KafkaListener(
            topics = "${spring.application.kafka.topic.orderservice.request}",
            containerFactory = "listenerContainerFactory")
    public void listen(@Payload OrderEventRequest orderEventRequest) {
        orderServiceAskNotificationKafkaService.sendNotification(orderEventRequest);
    }

    @KafkaListener(
            topics = "${spring.application.kafka.topic.factoryservice.response}",
            containerFactory = "factoryServiceListenerContainerFactory")
    public void receive(@Payload FactoryResponse factoryResponse) {
        factoryReceiveService.receive(factoryResponse);
    }

}

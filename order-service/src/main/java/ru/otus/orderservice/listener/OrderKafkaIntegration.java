package ru.otus.orderservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.otus.orderservice.dto.OrderEventRequestAsk;
import ru.otus.orderservice.dto.OrderEventResponse;
import ru.otus.orderservice.entity.Order;
import ru.otus.orderservice.entity.OrderRequestLog;
import ru.otus.orderservice.service.OrderRequestLogService;
import ru.otus.orderservice.service.OrderService;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderKafkaIntegration {

    private final OrderRequestLogService orderRequestLogService;

    private final OrderService orderService;

    @KafkaListener(
            topics = "${spring.application.kafka.topic.response}",
            containerFactory = "listenerContainerFactory")
    public void listen(@Payload OrderEventRequestAsk orderEventRequestAsk) {
        log.info("orderEventRequestAsk: {}", orderEventRequestAsk);
        orderRequestLogService.updateStatusByOperUID(orderEventRequestAsk);
    }


    @KafkaListener(
            topics = "${spring.application.kafka.topic.final}",
            containerFactory = "listenerContainerResponseFactory")
    public void listen(@Payload OrderEventResponse orderEventResponse) {
        log.info("orderEventResponse: {}", orderEventResponse);
        OrderRequestLog orderRequestLog
                = orderRequestLogService.findByOperUID(UUID.fromString(orderEventResponse.getOperUID()));
        orderRequestLog.setStatus("FINISH");
        Order order = orderRequestLog.getOrder();
        order.setStatus("Готов");
        orderRequestLogService.save(orderRequestLog);
        orderService.save(order);
    }
}

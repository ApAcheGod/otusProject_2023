package ru.otus.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.orderservice.dto.OrderEventRequest;
import ru.otus.orderservice.entity.Order;
import ru.otus.orderservice.entity.OrderRequestLog;
import ru.otus.orderservice.service.NotificationService;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class WarehouseKafkaNotificationService implements NotificationService {

    private final OrderRequestLogServiceImpl orderRequestLogService;

    private final KafkaTemplate<String, OrderEventRequest> kafkaTemplate;

    @Value("${spring.application.kafka.topic.request}")
    private String topic;

    @Override
    @Transactional
    public void sendRequest(Order order) {
        OrderRequestLog orderRequestLog = orderRequestLogService.save(toOrderRequestLog(order));
        OrderEventRequest orderEventRequest = toEvent(order, orderRequestLog.getRqUID(), orderRequestLog.getOperUID());
        try {
            log.info("Заказ {} отправка запроса на склад", order.getId());
            kafkaTemplate.send(topic, orderEventRequest)
                    .whenComplete(
                            (result, ex) -> {
                                if (ex == null) {
                                    log.info(
                                            "message id:{} was sent, offset:{}",
                                            orderEventRequest.getId(),
                                            result.getRecordMetadata().offset());

                                    orderRequestLog.setStatus("SENT");
                                    orderRequestLogService.save(orderRequestLog);
                                    log.info("{}", orderRequestLog.getRqUID());

                                } else {
                                    orderRequestLog.setStatus("ERROR");
                                    orderRequestLogService.save(orderRequestLog);
                                    log.error("message id:{} was not sent", orderEventRequest.getId(), ex);
                                    log.info("{}", orderRequestLog.getRqUID());
                                }
                            });
        } catch (Exception ex) {
            orderRequestLog.setStatus("ERROR");
            orderRequestLogService.save(orderRequestLog);
            log.error("message id:{} was not sent", orderEventRequest.getId(), ex);
            log.info("{}", orderRequestLog.getRqUID());
        }
    }

    private OrderRequestLog toOrderRequestLog(Order order) {
        OrderRequestLog orderRequestLog = new OrderRequestLog();
        orderRequestLog.setOrder(order);
        orderRequestLog.setOperUID(UUID.randomUUID());
        orderRequestLog.setRqUID(UUID.randomUUID());
        orderRequestLog.setStatus("CREATED");
        order.setOrderRequestLog(orderRequestLog);
        return orderRequestLog;
    }

    private OrderEventRequest toEvent(Order order, UUID rqUID, UUID operUID) {
        OrderEventRequest orderEventRequest = new OrderEventRequest();
        orderEventRequest.setId(String.valueOf(UUID.randomUUID()));
        orderEventRequest.setRqUID(String.valueOf(rqUID));
        orderEventRequest.setOperUID(String.valueOf(operUID));
        orderEventRequest.setCreatedAt(LocalDateTime.now());
        orderEventRequest.setProductId(String.valueOf(order.getProductId()));
        orderEventRequest.setAmount(order.getAmount());
        return orderEventRequest;
    }
}

package ru.otus.warehouseservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.otus.warehouseservice.dto.OrderEventRequest;
import ru.otus.warehouseservice.dto.OrderEventRequestAsk;
import ru.otus.warehouseservice.entity.Request;
import ru.otus.warehouseservice.mapper.OrderEventRequestAskMapper;
import ru.otus.warehouseservice.mapper.RequestMapper;
import ru.otus.warehouseservice.service.AskNotificationService;
import ru.otus.warehouseservice.service.RequestService;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceAskNotificationKafkaService implements AskNotificationService {

    private final KafkaTemplate<String, OrderEventRequestAsk> kafkaToOrderServiceAskTemplate;

    @Value("${spring.application.kafka.topic.orderservice.response}")
    private String topic;

    private final OrderEventRequestAskMapper orderEventRequestAskMapper;

    private final RequestMapper requestMapper;

    private final RequestService requestService;

    @Override
    public void sendNotification(OrderEventRequest orderEventRequest) {
        log.info("value: {}", orderEventRequest);
        Request request = requestMapper.toRequest(orderEventRequest);
        try {
            requestService.save(request);
            OrderEventRequestAsk orderEventRequestAsk = orderEventRequestAskMapper.toOrderEventRequestAsk(orderEventRequest, "OK");
            log.info("Отправка ответа на заказ {}", orderEventRequest.getOperUID());
            kafkaToOrderServiceAskTemplate.send(topic, orderEventRequestAsk)
                    .whenComplete(
                            (result, ex) -> {
                                if (ex == null) {
                                    log.info(
                                            "message id:{} was sent, offset:{}",
                                            orderEventRequest.getId(),
                                            result.getRecordMetadata().offset());
                                } else {
                                    log.error("message id:{} was not sent", orderEventRequest.getId(), ex);
                                }
                            });
        } catch (Exception ex) {
            log.error("message id:{} was not sent", orderEventRequest.getId(), ex);
        }
    }
}

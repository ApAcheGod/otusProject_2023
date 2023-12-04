package ru.otus.warehouseservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.otus.warehouseservice.dto.FactoryRequest;
import ru.otus.warehouseservice.entity.Request;
import ru.otus.warehouseservice.mapper.FactoryRequestMapper;
import ru.otus.warehouseservice.service.NotificationService;


@Slf4j
@Service
@RequiredArgsConstructor
public class FactoryServiceKafkaNotificationService implements NotificationService {

    private final KafkaTemplate<String, FactoryRequest> kafkaToFactoryServiceTemplate;

    private final RequestServiceImpl requestService;

    private final FactoryRequestMapper factoryRequestMapper;

    @Value("${spring.application.kafka.topic.factoryservice.request}")
    private String factoryTopic;

    @Override
    public void sendNotification(Request request) {
        FactoryRequest factoryRequest = factoryRequestMapper.toFactoryRequest(request);
        try {
            log.info("Отправка заказа на фабрику {}", factoryRequest.getId());
            kafkaToFactoryServiceTemplate.send(factoryTopic, factoryRequest)
                    .whenComplete(
                            (result, ex) -> {
                                if (ex == null) {
                                    request.setStatus("Заказано на фабрике");
                                    log.info(
                                            "message id:{} was sent, offset:{}",
                                            factoryRequest.getId(),
                                            result.getRecordMetadata().offset());
                                    requestService.save(request);
                                } else {
                                    log.error("message id:{} was not sent", factoryRequest.getId(), ex);
                                }
                            });
        } catch (Exception ex) {
            log.error("message id:{} was not sent", factoryRequest.getId(), ex);
        }
    }

}

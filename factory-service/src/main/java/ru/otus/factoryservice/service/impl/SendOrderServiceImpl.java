package ru.otus.factoryservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.otus.factoryservice.dto.FactoryResponse;
import ru.otus.factoryservice.entity.FactoryItemRequest;
import ru.otus.factoryservice.service.FactoryItemRequestService;
import ru.otus.factoryservice.service.SendOrderService;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendOrderServiceImpl implements SendOrderService {

    private final FactoryItemRequestService factoryItemRequestService;

    private final KafkaTemplate<String, FactoryResponse> kafkaTemplate;

    @Value("${spring.application.kafka.response}")
    private String topic;

    @Override
    public void process() {
        List<FactoryItemRequest> allCreatedRequests = factoryItemRequestService.findAllCreatedRequests();
        allCreatedRequests.forEach(factoryItemRequest -> {
            try {
                FactoryResponse factoryResponse = toFactoryResponse(factoryItemRequest);
                log.info("Заказ {} отправка заказа на склад", factoryItemRequest.getId());
                kafkaTemplate.send(topic, factoryResponse)
                        .whenComplete(
                                (result, ex) -> {
                                    if (ex == null) {
                                        log.info(
                                                "message id:{} was sent, offset:{}",
                                                factoryResponse.getId(),
                                                result.getRecordMetadata().offset());

                                        factoryItemRequest.setStatus("SENT");
                                        factoryItemRequestService.save(factoryItemRequest);
                                        log.info("{}", factoryItemRequest.getRqUID());

                                    } else {
                                        log.error("message id:{} was not sent", factoryItemRequest.getId(), ex);
                                        log.info("{}", factoryItemRequest.getRqUID());
                                    }
                                });
            } catch (Exception ex) {
                log.error("message id:{} was not sent", factoryItemRequest.getId(), ex);
                log.info("{}", factoryItemRequest.getRqUID());
            }
        });
    }

    private FactoryResponse toFactoryResponse(FactoryItemRequest factoryItemRequest) {
        FactoryResponse factoryResponse = new FactoryResponse();
        factoryResponse.setId(String.valueOf(factoryItemRequest.getId()));
        factoryResponse.setRqUID(String.valueOf(factoryItemRequest.getRqUID()));
        factoryResponse.setOperUID(String.valueOf(factoryItemRequest.getOperUID()));
        factoryResponse.setProductId(String.valueOf(factoryItemRequest.getProductId()));
        factoryResponse.setAmount(factoryItemRequest.getAmount());
        factoryResponse.setCreatedAt(LocalDateTime.now());
        return factoryResponse;

    }
}

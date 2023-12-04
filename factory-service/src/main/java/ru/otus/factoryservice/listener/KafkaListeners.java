package ru.otus.factoryservice.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import ru.otus.factoryservice.dto.FactoryRequest;
import ru.otus.factoryservice.mapper.FactoryItemRequestMapper;
import ru.otus.factoryservice.service.FactoryItemRequestService;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaListeners {

    private final FactoryItemRequestService factoryItemRequestService;

    private final FactoryItemRequestMapper factoryItemRequestMapper;

    @KafkaListener(
            topics = "${spring.application.kafka.request}",
            containerFactory = "listenerContainerFactory")
    public void listen(@Payload FactoryRequest factoryRequest) {
        log.info("{}", factoryRequest);
        factoryItemRequestService.save(factoryItemRequestMapper.toFactoryItemRequest(factoryRequest));
    }

}

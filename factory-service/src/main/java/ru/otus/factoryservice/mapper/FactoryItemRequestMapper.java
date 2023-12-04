package ru.otus.factoryservice.mapper;

import org.springframework.stereotype.Component;
import ru.otus.factoryservice.dto.FactoryRequest;
import ru.otus.factoryservice.entity.FactoryItemRequest;

import java.util.UUID;

@Component
public class FactoryItemRequestMapper {

    public FactoryItemRequest toFactoryItemRequest(FactoryRequest factoryRequest) {
        FactoryItemRequest factoryItemRequest = new FactoryItemRequest();
        factoryItemRequest.setId(UUID.randomUUID());
        factoryItemRequest.setProductId(UUID.fromString(factoryRequest.getProductId()));
        factoryItemRequest.setAmount(factoryRequest.getAmount());
        factoryItemRequest.setStatus("NEW");
        factoryItemRequest.setRqUID(UUID.fromString(factoryRequest.getRqUID()));
        factoryItemRequest.setOperUID(UUID.fromString(factoryRequest.getOperUID()));
        return factoryItemRequest;
    }
}

package ru.otus.warehouseservice.mapper;

import org.springframework.stereotype.Component;
import ru.otus.warehouseservice.dto.FactoryRequest;
import ru.otus.warehouseservice.entity.Request;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class FactoryRequestMapper {

    public FactoryRequest toFactoryRequest(Request request) {
        FactoryRequest factoryRequest = new FactoryRequest();
        factoryRequest.setId(String.valueOf(UUID.randomUUID()));
        factoryRequest.setRqUID(String.valueOf(UUID.randomUUID()));
        factoryRequest.setOperUID(String.valueOf(request.getOperUID()));
        factoryRequest.setAmount(request.getAmount());
        factoryRequest.setProductId(String.valueOf(request.getProductId()));
        factoryRequest.setCreatedAt(LocalDateTime.now());
        return factoryRequest;
    }
}

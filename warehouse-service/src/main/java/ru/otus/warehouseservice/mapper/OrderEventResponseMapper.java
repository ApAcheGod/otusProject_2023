package ru.otus.warehouseservice.mapper;

import org.springframework.stereotype.Component;
import ru.otus.warehouseservice.dto.OrderEventResponse;
import ru.otus.warehouseservice.entity.Request;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class OrderEventResponseMapper {

    public OrderEventResponse toOrderEventResponse(Request request) {
        OrderEventResponse orderEventResponse = new OrderEventResponse();
        orderEventResponse.setId(String.valueOf(UUID.randomUUID()));
        orderEventResponse.setRqUID(String.valueOf(UUID.randomUUID()));
        orderEventResponse.setOperUID(String.valueOf(request.getOperUID()));
        orderEventResponse.setProductId(String.valueOf(request.getProductId()));
        orderEventResponse.setAmount(request.getAmount());
        orderEventResponse.setCreatedAt(LocalDateTime.now());
        return orderEventResponse;
    }
}

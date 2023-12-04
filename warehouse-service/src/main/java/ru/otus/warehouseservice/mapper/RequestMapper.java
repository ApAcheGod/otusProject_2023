package ru.otus.warehouseservice.mapper;

import org.springframework.stereotype.Component;
import ru.otus.warehouseservice.dto.OrderEventRequest;
import ru.otus.warehouseservice.entity.Request;

import java.util.UUID;

@Component
public class RequestMapper {

    public Request toRequest(OrderEventRequest orderEventRequest) {
        Request request = new Request();
        request.setStatus("CREATED");
        request.setProductId(UUID.fromString(orderEventRequest.getProductId()));
        request.setAmount(orderEventRequest.getAmount());
        request.setOperUID(UUID.fromString(orderEventRequest.getOperUID()));
        return request;
    }
}

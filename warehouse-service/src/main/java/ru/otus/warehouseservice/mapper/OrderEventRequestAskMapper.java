package ru.otus.warehouseservice.mapper;

import org.springframework.stereotype.Component;
import ru.otus.warehouseservice.dto.OrderEventRequest;
import ru.otus.warehouseservice.dto.OrderEventRequestAsk;

import java.util.UUID;

@Component
public class OrderEventRequestAskMapper {

    public OrderEventRequestAsk toOrderEventRequestAsk(OrderEventRequest orderEventRequest, String status) {
        OrderEventRequestAsk orderEventRequestAsk = new OrderEventRequestAsk();
        orderEventRequestAsk.setId(String.valueOf(UUID.randomUUID()));
        orderEventRequestAsk.setRqUID(orderEventRequest.getRqUID());
        orderEventRequestAsk.setOperUID(orderEventRequest.getOperUID());
        orderEventRequestAsk.setStatus(status);
        return orderEventRequestAsk;
    }
}

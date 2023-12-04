package ru.otus.orderservice.service;

import ru.otus.orderservice.dto.OrderEventRequestAsk;
import ru.otus.orderservice.entity.OrderRequestLog;

import java.util.UUID;

public interface OrderRequestLogService {

    OrderRequestLog save(OrderRequestLog orderRequestLog);

    void updateStatusByOperUID(OrderEventRequestAsk orderEventRequestAsk);

    OrderRequestLog findByOperUID(UUID operUI);
}

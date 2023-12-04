package ru.otus.warehouseservice.service;

import ru.otus.warehouseservice.dto.OrderEventRequest;

public interface AskNotificationService {
    void sendNotification(OrderEventRequest orderEventRequest);
}

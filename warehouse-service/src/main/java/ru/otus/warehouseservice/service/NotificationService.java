package ru.otus.warehouseservice.service;

import ru.otus.warehouseservice.entity.Request;

public interface NotificationService {
    void sendNotification(Request request);
}

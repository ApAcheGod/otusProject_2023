package ru.otus.orderservice.service;

import ru.otus.orderservice.entity.Order;

public interface NotificationService {

    void sendRequest(Order order);
}

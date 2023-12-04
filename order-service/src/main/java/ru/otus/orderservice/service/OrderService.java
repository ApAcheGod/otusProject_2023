package ru.otus.orderservice.service;

import ru.otus.orderservice.entity.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderService {

    Order save(Order order);

    Optional<Order> findById(UUID id);
}

package ru.otus.orderservice.service;

import java.util.UUID;

public interface OrderRequestService {

    UUID request(UUID productId, Long amount);
}

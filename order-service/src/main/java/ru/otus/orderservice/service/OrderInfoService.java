package ru.otus.orderservice.service;

import ru.otus.orderservice.dto.OrderInfo;

import java.util.UUID;

public interface OrderInfoService {

    OrderInfo getOrderInfo(UUID id);
}

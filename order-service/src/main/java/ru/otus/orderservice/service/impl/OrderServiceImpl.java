package ru.otus.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.orderservice.entity.Order;
import ru.otus.orderservice.repository.OrderRepository;
import ru.otus.orderservice.service.OrderService;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return orderRepository.findById(id);
    }

}

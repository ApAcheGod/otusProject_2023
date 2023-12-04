package ru.otus.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.orderservice.entity.Order;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}

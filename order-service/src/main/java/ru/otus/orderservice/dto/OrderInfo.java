package ru.otus.orderservice.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderInfo(UUID id, String productName, Long amount, String status, LocalDateTime createdAt) {
}

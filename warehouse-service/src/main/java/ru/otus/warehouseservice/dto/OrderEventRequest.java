package ru.otus.warehouseservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderEventRequest {

    private String id;

    private String rqUID;

    private String operUID;

    private String productId;

    private Long amount;

    private LocalDateTime createdAt;
}

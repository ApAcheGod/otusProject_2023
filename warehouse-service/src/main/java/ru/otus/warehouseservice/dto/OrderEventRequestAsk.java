package ru.otus.warehouseservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderEventRequestAsk {


    private String id;

    private String rqUID;

    private String operUID;

    private String status;

    private LocalDateTime createdAt;
}

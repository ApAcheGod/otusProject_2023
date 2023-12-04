package ru.otus.orderservice.feignclient.productservice;

import lombok.Data;

import java.util.UUID;

@Data
public class Product {

    private UUID id;

    private String name;
}

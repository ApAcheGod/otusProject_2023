package ru.otus.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.orderservice.feignclient.productservice.Product;
import ru.otus.orderservice.feignclient.productservice.ProductClient;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductClient productClient;

    @GetMapping("/order/products")
    public List<Product> findAll() {
        return productClient.findAll();
    }
}

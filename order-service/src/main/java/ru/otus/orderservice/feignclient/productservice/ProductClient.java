package ru.otus.orderservice.feignclient.productservice;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient("product-service")
public interface ProductClient {

    @GetMapping("/products")
    List<Product> findAll();

    @Cacheable(cacheNames = "productsCache")
    @GetMapping("/products/{id}")
    Product findById(@PathVariable("id") UUID id);
}

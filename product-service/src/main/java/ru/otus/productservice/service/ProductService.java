package ru.otus.productservice.service;


import ru.otus.productservice.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(UUID id);
}

package ru.otus.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.productservice.entity.Product;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}

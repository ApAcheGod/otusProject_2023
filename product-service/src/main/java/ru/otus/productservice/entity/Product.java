package ru.otus.productservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.UuidGenerator;

import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "PRODUCT")
@Entity
public class Product {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Product product = (Product) o;
        return id != null && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

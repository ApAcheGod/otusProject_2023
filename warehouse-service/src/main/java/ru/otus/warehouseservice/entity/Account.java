package ru.otus.warehouseservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "ACCOUNT")
@Entity
public class Account {

    @Id
    @Column(name = "PRODUCT_ID", nullable = false)
    private UUID productId;

    @Column(name = "AMOUNT")
    private Long amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Account account = (Account) o;
        return productId != null && Objects.equals(productId, account.productId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

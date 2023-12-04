package ru.otus.warehouseservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "REQUEST")
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
public class Request {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    @Column(name = "operUID")
    private UUID operUID;

    @Column(name = "PRODUCT_ID")
    private UUID productId;

    @Column(name = "AMOUNT")
    private Long amount;

    @Column(name = "CREATED_AT")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "STATUS")
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Request request = (Request) o;
        return productId != null && Objects.equals(productId, request.productId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

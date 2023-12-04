package ru.otus.factoryservice.entity;

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
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "FACTORY_ITEM_REQUEST")
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
public class FactoryItemRequest {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    @Column(name = "rquid")
    private UUID rqUID;

    @Column(name = "operuid")
    private UUID operUID;

    @Column(name = "PRODUCT_ID")
    private UUID productId;

    @Column(name = "AMOUNT")
    private Long amount;

    @Column(name = "STATUS")
    private String status;

    @CreatedDate
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        FactoryItemRequest factoryItemRequest = (FactoryItemRequest) o;
        return id != null && Objects.equals(id, factoryItemRequest.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }


}

package ru.otus.orderservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(name = "ORDER_REQUEST_LOG")
@Entity
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
public class OrderRequestLog {

    @Id
    @UuidGenerator
    @GeneratedValue
    private UUID id;

    @Column(name = "rquid")
    private UUID rqUID;

    @Column(name = "operuid")
    private UUID operUID;

    @OneToOne
    @JoinColumn(name = "ORDER_ID")
    private Order order;

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
        OrderRequestLog orderRequestLog = (OrderRequestLog) o;
        return id != null && Objects.equals(id, orderRequestLog.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

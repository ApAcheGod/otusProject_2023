package ru.otus.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.orderservice.entity.OrderRequestLog;

import java.util.UUID;

public interface OrderRequestLogRepository extends JpaRepository<OrderRequestLog, UUID> {

    @Modifying
    @Transactional
    @Query("update OrderRequestLog rq set rq.status = :status where rq.rqUID = :rqUID")
    void updateOrderRequestLogByRqUID(UUID rqUID, String status);

    OrderRequestLog findByOperUID(UUID operUI);
}

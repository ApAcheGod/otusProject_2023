package ru.otus.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.orderservice.dto.OrderEventRequestAsk;
import ru.otus.orderservice.entity.OrderRequestLog;
import ru.otus.orderservice.repository.OrderRequestLogRepository;
import ru.otus.orderservice.service.OrderRequestLogService;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderRequestLogServiceImpl implements OrderRequestLogService {

    private final OrderRequestLogRepository orderRequestLogRepository;


    @Override
    public OrderRequestLog save(OrderRequestLog orderRequestLog) {
        return orderRequestLogRepository.save(orderRequestLog);
    }

    @Override
    @Transactional
    public void updateStatusByOperUID(OrderEventRequestAsk orderEventRequestAsk) {
        orderRequestLogRepository.updateOrderRequestLogByRqUID(
                UUID.fromString(orderEventRequestAsk.getRqUID()),
                orderEventRequestAsk.getStatus());
    }

    @Override
    public OrderRequestLog findByOperUID(UUID operUI) {
        return orderRequestLogRepository.findByOperUID(operUI);
    }


}

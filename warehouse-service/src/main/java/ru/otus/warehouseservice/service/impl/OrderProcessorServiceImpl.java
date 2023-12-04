package ru.otus.warehouseservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.warehouseservice.entity.Request;
import ru.otus.warehouseservice.service.NotificationService;
import ru.otus.warehouseservice.service.OrderProcessorService;
import ru.otus.warehouseservice.service.RequestService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProcessorServiceImpl implements OrderProcessorService {

    private final AccountServiceImpl accountService;

    private final RequestService requestService;

    private final NotificationService orderServiceKafkaNotificationService;
    
    private final NotificationService factoryServiceKafkaNotificationService;

    @Override
    @Transactional
    public void findNewRequestsAndSendToKafka() {
        List<Request> allCreatedRequests = requestService.findAllCreatedRequests();
        log.info("Найдено {} новых заказов", allCreatedRequests.size());
        allCreatedRequests.forEach(request -> {
            Boolean exists = accountService.existsByProductIdAndAmountGreaterThanEqual(request.getProductId(), request.getAmount());
            if (exists) {
                orderServiceKafkaNotificationService.sendNotification(request);
            } else {
                factoryServiceKafkaNotificationService.sendNotification(request);
            }
        });
    }
}

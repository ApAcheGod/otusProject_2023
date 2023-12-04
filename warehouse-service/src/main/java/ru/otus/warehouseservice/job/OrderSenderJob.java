package ru.otus.warehouseservice.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.warehouseservice.entity.Request;
import ru.otus.warehouseservice.service.NotificationService;
import ru.otus.warehouseservice.service.RequestService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderSenderJob {

    private final RequestService requestService;

    private final NotificationService orderServiceKafkaNotificationService;

    @Scheduled(cron = "${job.orderNotify.cron}")
    public void start() {
        List<Request> allByReceivedRequests = requestService.findAllByReceivedRequests();
        allByReceivedRequests.forEach(orderServiceKafkaNotificationService::sendNotification);
    }

}

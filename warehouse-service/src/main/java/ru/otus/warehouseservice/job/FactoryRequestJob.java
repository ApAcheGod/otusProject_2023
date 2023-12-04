package ru.otus.warehouseservice.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.warehouseservice.service.OrderProcessorService;

@Slf4j
@Service
@RequiredArgsConstructor
public class FactoryRequestJob {

    private final OrderProcessorService orderProcessorService;

    @Scheduled(cron = "${job.factoryRequest.cron}")
    public void request() {
        log.info("Запуск джобы для запроса на фабрике");
        orderProcessorService.findNewRequestsAndSendToKafka();
    }

}

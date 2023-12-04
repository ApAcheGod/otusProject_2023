package ru.otus.factoryservice.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.factoryservice.service.SendOrderService;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendOrderJob {

    private final SendOrderService sendOrderService;

    @Scheduled(cron = "${job.sender.cron}")
    public void start() {
        log.info("Джоба по отправке заказов запущена");
        sendOrderService.process();
    }
}

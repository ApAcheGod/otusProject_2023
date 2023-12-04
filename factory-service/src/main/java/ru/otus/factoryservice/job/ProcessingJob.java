package ru.otus.factoryservice.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.factoryservice.service.ProcessService;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessingJob {

    private final ProcessService processService;

    @Scheduled(cron = "${job.process.cron}")
    public void start() {
        log.info("Джоба по изготовлению заказов запущена");
        processService.process();
    }

}

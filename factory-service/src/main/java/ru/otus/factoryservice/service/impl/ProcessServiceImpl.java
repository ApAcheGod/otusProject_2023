package ru.otus.factoryservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.factoryservice.entity.FactoryItemRequest;
import ru.otus.factoryservice.service.FactoryItemRequestService;
import ru.otus.factoryservice.service.ProcessService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessServiceImpl implements ProcessService {

    private final FactoryItemRequestService factoryItemRequestService;

    @Override
    public void process() {
        List<FactoryItemRequest> allNewRequests = factoryItemRequestService.findAllNewRequests();
        log.info("Найдено {} новых заказов", allNewRequests.size());
        allNewRequests.forEach(this::create);
    }

    private void create(FactoryItemRequest factoryItemRequest) {
        try {
            log.info("Производим заказ {} {} шт", factoryItemRequest.getProductId(), factoryItemRequest.getAmount());
            Thread.sleep(10000);
            factoryItemRequest.setStatus("CREATED");
            factoryItemRequestService.save(factoryItemRequest);
        } catch (InterruptedException e) {
            log.error("Ошибка при производстве заказа {}",factoryItemRequest.getId(), e);
        }
    }
}

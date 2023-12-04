package ru.otus.factoryservice.service;

import ru.otus.factoryservice.entity.FactoryItemRequest;

import java.util.List;

public interface FactoryItemRequestService {

    FactoryItemRequest save(FactoryItemRequest factoryItemRequest);

    List<FactoryItemRequest> findAllNewRequests();

    List<FactoryItemRequest> findAllCreatedRequests();
}

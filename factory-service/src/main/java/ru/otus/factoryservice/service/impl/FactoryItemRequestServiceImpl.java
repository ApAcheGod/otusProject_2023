package ru.otus.factoryservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.factoryservice.entity.FactoryItemRequest;
import ru.otus.factoryservice.repository.FactoryItemRequestRepository;
import ru.otus.factoryservice.service.FactoryItemRequestService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FactoryItemRequestServiceImpl implements FactoryItemRequestService {

    private final FactoryItemRequestRepository factoryItemRequestRepository;

    @Override
    public FactoryItemRequest save(FactoryItemRequest factoryItemRequest) {
        return factoryItemRequestRepository.save(factoryItemRequest);
    }

    @Override
    public List<FactoryItemRequest> findAllNewRequests() {
        return factoryItemRequestRepository.findAllNewRequests();
    }

    @Override
    public List<FactoryItemRequest> findAllCreatedRequests() {
        return factoryItemRequestRepository.findAllCreatedRequests();
    }
}

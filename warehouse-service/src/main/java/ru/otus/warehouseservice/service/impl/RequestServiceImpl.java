package ru.otus.warehouseservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.warehouseservice.entity.Request;
import ru.otus.warehouseservice.repository.RequestRepository;
import ru.otus.warehouseservice.service.RequestService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    @Override
    public Request save(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public Request findByOperUID(UUID rqUID) {
        return requestRepository.findByOperUID(rqUID);
    }

    @Override
    public List<Request> findAllCreatedRequests() {
        return requestRepository.findAllCreatedRequests();
    }

    @Override
    public List<Request> findAllByReceivedRequests() {
        return requestRepository.findAllByReceived();
    }
}

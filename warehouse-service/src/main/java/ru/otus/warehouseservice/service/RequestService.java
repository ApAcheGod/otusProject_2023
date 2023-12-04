package ru.otus.warehouseservice.service;

import ru.otus.warehouseservice.entity.Request;

import java.util.List;
import java.util.UUID;

public interface RequestService {

    Request save(Request request);

    Request findByOperUID(UUID rqUID);

    List<Request> findAllCreatedRequests();

    List<Request> findAllByReceivedRequests();
}

package ru.otus.warehouseservice.service;

public interface OrderProcessorService {

    void findNewRequestsAndSendToKafka();
}

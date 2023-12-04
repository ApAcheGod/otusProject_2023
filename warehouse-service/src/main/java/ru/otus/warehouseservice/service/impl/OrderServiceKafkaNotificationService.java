package ru.otus.warehouseservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.otus.warehouseservice.dto.OrderEventResponse;
import ru.otus.warehouseservice.entity.Account;
import ru.otus.warehouseservice.entity.Request;
import ru.otus.warehouseservice.mapper.OrderEventResponseMapper;
import ru.otus.warehouseservice.service.AccountService;
import ru.otus.warehouseservice.service.NotificationService;
import ru.otus.warehouseservice.service.RequestService;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceKafkaNotificationService implements NotificationService {

    private final KafkaTemplate<String, OrderEventResponse> kafkaToOrderServiceResponseTemplate;

    private final AccountService accountService;

    private final RequestService requestService;

    private final OrderEventResponseMapper orderEventResponseMapper;

    @Value("${spring.application.kafka.topic.orderservice.final}")
    private String orderTopic;

    @Override
    public void sendNotification(Request request) {
        OrderEventResponse orderEventResponse = orderEventResponseMapper.toOrderEventResponse(request);
        Optional<Account> accountOptional = accountService.findById(request.getProductId());
        Account account;
        if (accountOptional.isPresent()) {
            account = accountOptional.get();
            account.setAmount(accountOptional.get().getAmount() - request.getAmount());
            accountService.save(accountOptional.get());
        } else {
            account = new Account();
            account.setProductId(request.getProductId());
            account.setAmount(request.getAmount());
            accountService.save(account);
        }
        try {
            log.info("Отправка заказа в order-service {}", orderEventResponse.getId());
            kafkaToOrderServiceResponseTemplate.send(orderTopic, orderEventResponse)
                    .whenComplete(
                            (result, ex) -> {
                                if (ex == null) {
                                    request.setStatus("Заказ отправлен");
                                    log.info(
                                            "message id:{} was sent, offset:{}",
                                            orderEventResponse.getId(),
                                            result.getRecordMetadata().offset());
                                    requestService.save(request);
                                } else {
                                    account.setAmount(account.getAmount() + request.getAmount());
                                    accountService.save(account);
                                    log.error("message id:{} was not sent", orderEventResponse.getId(), ex);
                                }
                            });
        } catch (Exception ex) {
            account.setAmount(account.getAmount() + request.getAmount());
            accountService.save(account);
            log.error("message id:{} was not sent", orderEventResponse.getId(), ex);
        }
    }
}

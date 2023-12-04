package ru.otus.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.orderservice.entity.Order;
import ru.otus.orderservice.feignclient.userservice.UserClient;
import ru.otus.orderservice.feignclient.userservice.UserInfo;
import ru.otus.orderservice.service.NotificationService;
import ru.otus.orderservice.service.OrderRequestService;
import ru.otus.orderservice.service.OrderService;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderRequestServiceImpl implements OrderRequestService {


    private final NotificationService warehouseKafkaNotificationService;

    private final UserClient userClient;

    private final OrderService orderService;

    @Override
    public UUID request(UUID productId, Long amount) {
        UserInfo user = userClient.getUser();
        Order order = createOrder(productId, amount, user.getId());
        orderService.save(order);
        warehouseKafkaNotificationService.sendRequest(order);
        order.setStatus("Запрос отправлен на склад");
        return orderService.save(order).getId();
    }

    private Order createOrder(UUID productId, Long amount, Long userId) {
        Order order = new Order();
        order.setProductId(productId);
        order.setAmount(amount);
        order.setUserId(userId);
        order.setStatus("Создан");
        return order;
    }
}

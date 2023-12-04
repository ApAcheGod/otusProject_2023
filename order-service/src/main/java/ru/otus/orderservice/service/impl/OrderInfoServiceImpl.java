package ru.otus.orderservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.orderservice.dto.OrderInfo;
import ru.otus.orderservice.entity.Order;
import ru.otus.orderservice.feignclient.productservice.Product;
import ru.otus.orderservice.feignclient.productservice.ProductClient;
import ru.otus.orderservice.service.OrderInfoService;
import ru.otus.orderservice.service.OrderService;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderInfoServiceImpl implements OrderInfoService {

    private final OrderService orderService;

    private final ProductClient productClient;

    @Override
    public OrderInfo getOrderInfo(UUID id) {
        Optional<Order> orderOptional = orderService.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            Product product = productClient.findById(order.getProductId());
            if (Objects.nonNull(product)) {
                return new OrderInfo(order.getId(), product.getName(), order.getAmount(), order.getStatus(), order.getCreatedAt());
            }
        }
        return null;
    }
}

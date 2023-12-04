package ru.otus.orderservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.orderservice.dto.OrderInfo;
import ru.otus.orderservice.service.OrderInfoService;
import ru.otus.orderservice.service.OrderRequestService;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderRequestController {

    private final OrderRequestService orderRequestService;

    private final OrderInfoService orderInfoService;

    @GetMapping(value = "/order", params = {"productId", "amount"})
    public UUID order(@RequestParam(value = "productId") UUID productId,
                         @RequestParam(value = "amount") Long amount) {
        return orderRequestService.request(productId, amount);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderInfo> getOrderInfo(@PathVariable("orderId") UUID orderId) {
        return ResponseEntity.ok(orderInfoService.getOrderInfo(orderId));
    }
}

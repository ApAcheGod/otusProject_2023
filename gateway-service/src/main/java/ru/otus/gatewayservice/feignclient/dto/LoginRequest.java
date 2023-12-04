package ru.otus.gatewayservice.feignclient.dto;

public record LoginRequest(String userName, String password) {
}

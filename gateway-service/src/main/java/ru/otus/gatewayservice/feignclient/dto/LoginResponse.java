package ru.otus.gatewayservice.feignclient.dto;

public record LoginResponse(String message, String accessJwtToken, String refreshJwtToken) {
}

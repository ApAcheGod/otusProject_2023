package ru.otus.authenticationservice.dto;

public record LoginResponse(String message, String accessJwtToken, String refreshJwtToken) {
}

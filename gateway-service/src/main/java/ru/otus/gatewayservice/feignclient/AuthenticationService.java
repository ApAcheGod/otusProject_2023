package ru.otus.gatewayservice.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.gatewayservice.feignclient.dto.LoginRequest;
import ru.otus.gatewayservice.feignclient.dto.LoginResponse;

@FeignClient("authentication-service")
public interface AuthenticationService {

    @PostMapping("/auth/login")
    LoginResponse login(LoginRequest loginRequest);

    @GetMapping("/auth/validate")
    String validate(@RequestParam String token);
}

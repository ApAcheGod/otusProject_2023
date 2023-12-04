package ru.otus.orderservice.feignclient.userservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("authentication-service")
public interface UserClient {

    @GetMapping("/auth/user")
    UserInfo getUser();
}

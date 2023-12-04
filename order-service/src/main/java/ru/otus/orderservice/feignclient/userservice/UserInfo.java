package ru.otus.orderservice.feignclient.userservice;

import lombok.Data;

@Data
public class UserInfo {

    private Long id;

    private String userName;

    private String email;

}

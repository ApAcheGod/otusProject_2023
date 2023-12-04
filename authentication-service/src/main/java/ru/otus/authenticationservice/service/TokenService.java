package ru.otus.authenticationservice.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {

    String generateAccessToken(UserDetails user);

    String generateRefreshToken(UserDetails user);

    String parseToken(String token);
}

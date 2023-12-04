package ru.otus.authenticationservice.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.otus.authenticationservice.dto.LoginRequest;

import java.util.Optional;

public interface AuthenticationService {

    Optional<UserDetails> login(LoginRequest loginRequest);
}

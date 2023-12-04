package ru.otus.authenticationservice.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.otus.authenticationservice.dto.LoginRequest;
import ru.otus.authenticationservice.service.AuthenticationService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    public Optional<UserDetails> login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.userName(), loginRequest.password());
        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            log.error("Authenticate error for user {}", loginRequest.userName(), e);
            return Optional.empty();
        }
        return Optional.ofNullable(userDetailsService.loadUserByUsername(loginRequest.userName()));
    }
}

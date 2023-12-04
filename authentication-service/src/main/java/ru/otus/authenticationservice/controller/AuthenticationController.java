package ru.otus.authenticationservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.otus.authenticationservice.dto.LoginRequest;
import ru.otus.authenticationservice.dto.LoginResponse;
import ru.otus.authenticationservice.dto.UserInfo;
import ru.otus.authenticationservice.service.AuthenticationService;
import ru.otus.authenticationservice.service.Impl.UserServiceImpl;
import ru.otus.authenticationservice.service.TokenService;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    private final TokenService tokenService;

    private final UserServiceImpl userServiceImpl;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        if (!loginRequest.userName().isEmpty() || !loginRequest.password().isEmpty()) {
            Optional<UserDetails> optionalUserDetails = authenticationService.login(loginRequest);
            return optionalUserDetails.map(userDetails -> ResponseEntity.ok(
                    new LoginResponse(
                            loginRequest.userName(),
                            tokenService.generateAccessToken(userDetails),
                            tokenService.generateRefreshToken(userDetails))
            )).orElseGet(() ->
                    ResponseEntity.badRequest().body(new LoginResponse("Login error", null, null)));
        } else {
            return ResponseEntity.badRequest().body(new LoginResponse("Login error", null, null));
        }
    }

    @GetMapping("/auth/validate")
    public ResponseEntity<String> validate(@RequestParam String token) {
        return ResponseEntity.ok(tokenService.parseToken(token));
    }

    @GetMapping("/auth/user")
    public UserInfo getUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String authorization = request.getHeader("Authorization");
        return userServiceImpl.getUserInfo(tokenService.parseToken(authorization.substring(7)));
    }
}

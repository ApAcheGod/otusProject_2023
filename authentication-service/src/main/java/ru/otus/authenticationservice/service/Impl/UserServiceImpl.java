package ru.otus.authenticationservice.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.authenticationservice.dto.UserInfo;
import ru.otus.authenticationservice.entity.User;
import ru.otus.authenticationservice.repository.UserRepository;
import ru.otus.authenticationservice.service.UserService;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUserName(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                    user.getRoles()
                            .stream()
                            .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                            .collect(Collectors.toSet()));
        } else {
            log.error("User {} not found", username);
            throw new UsernameNotFoundException("User " + username + " not found");
        }
    }

    public UserInfo getUserInfo(String username) {
        Optional<User> optionalUser = userRepository.findByUserName(username);
        if (optionalUser.isPresent()) {
            return new UserInfo(optionalUser.get().getId(), optionalUser.get().getUserName(), optionalUser.get().getEmail());
        } else {
            log.error("User {} not found", username);
            throw new UsernameNotFoundException("User " + username + " not found");
        }
    }
}

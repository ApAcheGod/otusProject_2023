package ru.otus.authenticationservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.authenticationservice.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);
}

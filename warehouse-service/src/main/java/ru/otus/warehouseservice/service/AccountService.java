package ru.otus.warehouseservice.service;

import ru.otus.warehouseservice.entity.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountService {

    Boolean existsByProductIdAndAmountGreaterThanEqual(UUID productId, Long amount);

    Optional<Account> findById(UUID id);

    Account save(Account account);
}

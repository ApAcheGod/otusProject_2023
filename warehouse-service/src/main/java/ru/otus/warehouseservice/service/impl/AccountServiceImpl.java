package ru.otus.warehouseservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.warehouseservice.entity.Account;
import ru.otus.warehouseservice.repository.AccountRepository;
import ru.otus.warehouseservice.service.AccountService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    public Boolean existsByProductIdAndAmountGreaterThanEqual(UUID productId, Long amount) {
        return accountRepository.existsByProductIdAndAmountGreaterThanEqual(productId, amount);
    }

    @Override
    public Optional<Account> findById(UUID id) {
        return accountRepository.findById(id);
    }

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }
}

package ru.otus.warehouseservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.warehouseservice.dto.FactoryResponse;
import ru.otus.warehouseservice.entity.Account;
import ru.otus.warehouseservice.entity.Request;
import ru.otus.warehouseservice.service.AccountService;
import ru.otus.warehouseservice.service.FactoryReceiveService;
import ru.otus.warehouseservice.service.RequestService;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FactoryReceiveServiceImpl implements FactoryReceiveService {

    private final RequestService requestService;

    private final AccountService accountService;

    @Override
    public void receive(FactoryResponse factoryResponse) {
        log.info("FactoryResponse {} ", factoryResponse);
        Request request = requestService.findByOperUID(UUID.fromString(factoryResponse.getOperUID()));
        request.setStatus("Прибыло на склад");
        requestService.save(request);
        Optional<Account> accountOptional = accountService.findById(request.getProductId());
        if (accountOptional.isPresent()) {
            accountOptional.get().setAmount(accountOptional.get().getAmount() + request.getAmount());
        } else {
            Account account = new Account();
            account.setProductId(request.getProductId());
            account.setAmount(request.getAmount());
            accountService.save(account);
        }
    }
}

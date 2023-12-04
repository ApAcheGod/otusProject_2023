package ru.otus.warehouseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.warehouseservice.entity.Account;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    Boolean existsByProductIdAndAmountGreaterThanEqual(UUID productId, Long amount);

    @Modifying
    @Transactional
    @Query("update Account a set a.amount = a.amount - :amount where a.productId = :productId")
    void updateAccountByProductId(UUID productId, Long amount);
}

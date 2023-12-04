package ru.otus.factoryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.factoryservice.entity.FactoryItemRequest;

import java.util.List;
import java.util.UUID;

public interface FactoryItemRequestRepository extends JpaRepository<FactoryItemRequest, UUID> {


    @Query("select f from FactoryItemRequest f where f.status = 'NEW' ")
    List<FactoryItemRequest> findAllNewRequests();

    @Query("select f from FactoryItemRequest f where f.status = 'CREATED' ")
    List<FactoryItemRequest> findAllCreatedRequests();
}

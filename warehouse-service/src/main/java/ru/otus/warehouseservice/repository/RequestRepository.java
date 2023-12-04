package ru.otus.warehouseservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.warehouseservice.entity.Request;

import java.util.List;
import java.util.UUID;

public interface RequestRepository extends JpaRepository<Request, UUID> {

    @Query("select r from Request r where r.status = 'CREATED'")
    List<Request> findAllCreatedRequests();

    Request findByOperUID(UUID operUID);

    @Query("select r from Request r where r.status = 'Прибыло на склад'")
    List<Request> findAllByReceived();
}

package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface ClientRepository extends MongoRepository<Client, String> {
    boolean existsByEmail(String email);

    Optional<Client> findByEmail(String email);

    Optional<Client> findByCi(String ci);

    boolean existsByCi(String ci);
}

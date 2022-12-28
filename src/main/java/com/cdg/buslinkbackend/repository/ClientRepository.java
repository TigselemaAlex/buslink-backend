package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.Client;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface ClientRepository extends CrudRepository<Client, String> {
    boolean existsByEmail(String email);

    Optional<Client> findByEmail(String email);
}

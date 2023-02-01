package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

// A repository that extends the MongoRepository interface.

public interface ClientRepository extends MongoRepository<Client, String> {
    /**
     * Check if there is a user with the given email.
     * 
     * @param email The email to check.
     * @return A boolean value.
     */
    boolean existsByEmail(String email);

    /**
     * Find a client by email, and return it wrapped in an Optional.
     * 
     * @param email The email of the client to be found.
     * @return Optional<Client>
     */
    Optional<Client> findByEmail(String email);

    /**
     * Find a client by its ci.
     * 
     * @param ci The client's ci.
     * @return Optional<Client>
     */
    Optional<Client> findByCi(String ci);

    /**
     * > Returns true if there is a user with the given ci
     * 
     * @param ci The ci of the user to check.
     * @return A boolean value.
     */
    boolean existsByCi(String ci);
}

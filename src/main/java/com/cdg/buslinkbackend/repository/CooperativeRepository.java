package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.Cooperative;
import org.springframework.data.mongodb.repository.MongoRepository;

// A repository for the Cooperative entity. It is a MongoDB repository.
public interface CooperativeRepository extends MongoRepository<Cooperative, String> {
    /**
     * Returns true if there is a user with the given name.
     * 
     * @param name The name of the user to check for.
     * @return A boolean value.
     */
    boolean existsByName(String name);

}

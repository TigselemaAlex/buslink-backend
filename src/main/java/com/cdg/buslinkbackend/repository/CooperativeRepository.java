package com.cdg.buslinkbackend.repository;


import com.cdg.buslinkbackend.model.entity.Cooperative;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CooperativeRepository extends MongoRepository<Cooperative, String> {
    boolean existsByName(String name);
}

package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends MongoRepository<User, String> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername (String username);

    List<User> findByRoleIn(Collection<String> role);

}

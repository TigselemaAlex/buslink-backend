package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface UserRepository extends CrudRepository<User, String> {

    boolean existsByUsername(String username);
}

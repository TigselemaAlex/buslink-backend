package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.User;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@EnableScan
public interface UserRepository extends CrudRepository<User, String> {

    boolean existsByUsername(String username);

    Optional<User> findByUsername (String username);

    List<User> findByRoleIn(Collection<String> role);
}

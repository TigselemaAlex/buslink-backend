package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

// A repository interface that extends the MongoRepository interface.
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Check if a user exists by username.
     * 
     * @param username The username to check for.
     * @return A boolean value.
     */
    boolean existsByUsername(String username);

    /**
     * Find a user by their username, and return an Optional that contains the user
     * if found, or an
     * empty Optional if not found.
     * 
     * @param username The username of the user you want to find.
     * @return Optional<User>
     */
    Optional<User> findByUsername(String username);

    /**
     * Find all users with a role in the given collection of roles.
     * 
     * @param role The role to search for.
     * @return A list of users with the specified role.
     */
    List<User> findByRoleIn(Collection<String> role);

}

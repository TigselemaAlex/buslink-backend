package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.Role;
import com.cdg.buslinkbackend.model.enums.RoleType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

// A repository for the Role entity.
@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    /**
     * Find all roles whose name is in the given collection of role types.
     * 
     * @param name The name of the role.
     * @return A list of roles
     */
    List<Role> findByNameIn(Collection<RoleType> name);

}

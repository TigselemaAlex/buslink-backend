package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.Role;
import com.cdg.buslinkbackend.model.enums.RoleType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    List<Role> findByNameIn(Collection<RoleType> name);

}

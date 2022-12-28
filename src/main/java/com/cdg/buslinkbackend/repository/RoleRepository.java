package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.Role;
import com.cdg.buslinkbackend.model.enums.RoleType;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

@EnableScan
public interface RoleRepository extends CrudRepository<Role, String> {

    List<Role> findByNameIn(Collection<RoleType> name);
}

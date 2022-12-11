package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.Role;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface RoleRepository extends CrudRepository<Role, String> {
}

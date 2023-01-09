package com.cdg.buslinkbackend.repository;


import com.cdg.buslinkbackend.model.entity.Cooperative;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@EnableScan
@Repository
public interface CooperativeRepository extends CrudRepository<Cooperative, String> {
    boolean existsByName(String name);
}

package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.Bus;
import com.cdg.buslinkbackend.model.entity.Cooperative;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusRepository extends MongoRepository<Bus, String> {

    boolean existsByBusNumber(Integer busNumber);

    long countByCooperative(Cooperative cooperative);

    List<Bus> findAllByCooperative(Cooperative cooperative);

    Optional<Bus> findByBusNumber(Integer busNumber);

    long countAllByCooperativeIsNull();

}

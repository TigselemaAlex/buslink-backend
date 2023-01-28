package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.Bus;
import com.cdg.buslinkbackend.model.entity.Cooperative;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusRepository extends MongoRepository<Bus, String> {

    boolean existsByBusNumber(Integer busNumber);

    long countByCooperative(Cooperative cooperative);

    List<Bus> findAllByCooperative(Cooperative cooperative);

    @Query("{'itineraries.$frequency.destiny': ?0}")
    List<Bus> findAllByDestiny(String destiny);

    @Query("{'itineraries.$frequency.origen': ?0 , 'itineraries.frequency.destiny': ?1} ")
    List<Bus> findAllByOrigenAndDestiny(String origen, String destiny);


}

package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.Frequency;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FrequencyRespository extends MongoRepository<Frequency, String> {

    Optional<Frequency> findByDestinyAndOrigenAndStops(String destiny, String origen, List<String> stops);
}

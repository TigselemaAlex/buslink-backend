package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.Bus;
import com.cdg.buslinkbackend.model.entity.Frequency;
import com.cdg.buslinkbackend.model.entity.Itinerary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ItineraryRepository extends MongoRepository<Itinerary, String> {
    Optional<Itinerary> findByBusAndFrequencyAndDepartureTime(Bus bus, Frequency frequency, String departureTime);


}

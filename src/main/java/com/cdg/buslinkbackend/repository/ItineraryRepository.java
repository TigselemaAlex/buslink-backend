package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.Bus;
import com.cdg.buslinkbackend.model.entity.Frequency;
import com.cdg.buslinkbackend.model.entity.Itinerary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

// A repository for the Itinerary class. It is a class that extends the MongoRepository class. It is a
// class that is used to access the database.
public interface ItineraryRepository extends MongoRepository<Itinerary, String> {
    /**
     * Find an itinerary by bus, frequency, and departure time.
     * 
     * @param bus           The bus object that you want to find the itinerary for.
     * @param frequency     The frequency of the bus.
     * @param departureTime The time of departure of the bus from the source
     *                      station.
     * @return Optional<Itinerary>
     */
    Optional<Itinerary> findByBusAndFrequencyAndDepartureTime(Bus bus, Frequency frequency, String departureTime);

}

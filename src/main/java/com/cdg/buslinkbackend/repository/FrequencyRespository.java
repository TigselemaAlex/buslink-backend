package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.Frequency;
import com.cdg.buslinkbackend.model.enums.FrequencyType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

// A repository that extends the MongoRepository. It is a class that is used to access the database.
public interface FrequencyRespository extends MongoRepository<Frequency, String> {

    /**
     * Find a frequency by destiny, origen, stops and type.
     * 
     * @param destiny The destiny of the flight.
     * @param origen  The origin of the flight
     * @param stops   List<String>
     * @param type    The type of frequency, either "direct" or "indirect"
     * @return Optional<Frequency>
     */
    Optional<Frequency> findByDestinyAndOrigenAndStopsAndType(String destiny, String origen, List<String> stops,
            FrequencyType type);

    /**
     * Find a frequency by destiny, origen, stops, price, hours, minutes and type
     * 
     * @param destiny The destiny of the flight
     * @param origen  The origin of the flight
     * @param stops   List of stops
     * @param price   The price of the flight
     * @param hours   Integer
     * @param minutes Integer
     * @param type    FrequencyType
     * @return Optional&lt;Frequency&gt;
     */
    Optional<Frequency> findByDestinyAndOrigenAndStopsAndPriceAndHoursAndMinutesAndType(String destiny, String origen,
            List<String> stops, Double price, Integer hours, Integer minutes, FrequencyType type);
}

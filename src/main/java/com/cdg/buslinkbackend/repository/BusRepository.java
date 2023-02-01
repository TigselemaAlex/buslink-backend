package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.Bus;
import com.cdg.buslinkbackend.model.entity.Cooperative;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// A repository for the Bus entity.
@Repository
public interface BusRepository extends MongoRepository<Bus, String> {

    /**
     * > Returns true if a bus with the given bus number exists in the database
     * 
     * @param busNumber The bus number to check for.
     * @return A boolean value.
     */
    boolean existsByBusNumber(Integer busNumber);

    /**
     * Count the number of entities of type `Cooperative`.
     * 
     * @param cooperative The cooperative to search for.
     * @return The number of rows in the table.
     */
    long countByCooperative(Cooperative cooperative);

    /**
     * Find all buses that belong to a cooperative.
     * 
     * @param cooperative The cooperative to search for buses
     * @return A list of buses that belong to a cooperative.
     */
    List<Bus> findAllByCooperative(Cooperative cooperative);

    /**
     * Find a bus by its number, and return it if it exists, otherwise return null.
     * 
     * @param busNumber The bus number of the bus you want to find.
     * @return Optional<Bus>
     */
    Optional<Bus> findByBusNumber(Integer busNumber);

    /**
     * Count all the entities where the cooperative is null.
     * 
     * @return A count of all the records in the database where the cooperative is
     *         null.
     */
    long countAllByCooperativeIsNull();

}

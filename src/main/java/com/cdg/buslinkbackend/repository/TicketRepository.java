package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.Client;
import com.cdg.buslinkbackend.model.entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

// A repository interface that extends the MongoRepository interface.
public interface TicketRepository extends MongoRepository<Ticket, String> {
    /**
     * Find all tickets for a given client.
     * 
     * @param client The client to search for.
     * @return A list of tickets.
     */
    List<Ticket> findByClient(Client client);
}

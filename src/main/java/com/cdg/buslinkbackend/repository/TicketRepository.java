package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.Client;
import com.cdg.buslinkbackend.model.entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findByClient(Client client);
}

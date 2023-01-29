package com.cdg.buslinkbackend.repository;

import com.cdg.buslinkbackend.model.entity.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepository extends MongoRepository<Ticket, String> {
}

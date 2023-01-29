package com.cdg.buslinkbackend.service.ticket;

import com.cdg.buslinkbackend.model.request.ticket.TicketRequestDTO;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface TicketService {

    ResponseEntity<ApiResponse> saveTicket(TicketRequestDTO ticketRequestDTO);
}

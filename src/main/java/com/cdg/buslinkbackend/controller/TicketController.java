package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.model.request.ticket.TicketRequestDTO;
import com.cdg.buslinkbackend.service.ticket.TicketService;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(@RequestBody final TicketRequestDTO ticketRequestDTO){
        return ticketService.saveTicket(ticketRequestDTO);

    }
}

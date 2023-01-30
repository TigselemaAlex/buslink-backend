package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.model.request.ticket.TicketReceiptRequestDTO;
import com.cdg.buslinkbackend.model.request.ticket.TicketRequestDTO;
import com.cdg.buslinkbackend.service.ticket.TicketService;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @PutMapping
    public ResponseEntity<ApiResponse> setReciept(
            @RequestParam final String id,
            @RequestParam final MultipartFile receipt) throws IOException{
        return ticketService.setReciept(new TicketReceiptRequestDTO(id, receipt));
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAll(){
        return ticketService.findAll();
    }

    @PutMapping(value = "/verify/{id}")
    public ResponseEntity<ApiResponse> vericyReceipt(@PathVariable String id) throws IOException, WriterException{
        return  ticketService.verifyReceipt(id);
    }

    @GetMapping(value = "/client/{id}")
    public ResponseEntity<ApiResponse> findByClient(@PathVariable String id){
        return ticketService.findByClient(id);
    }
}

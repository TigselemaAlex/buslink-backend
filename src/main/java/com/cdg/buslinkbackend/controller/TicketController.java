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

/**
 * It's a controller class that handles all the requests that are sent to the
 * server.
 */
@RestController
@RequestMapping("/protected/tickets")
public class TicketController {

    private final TicketService ticketService;

    // A constructor that is being used to inject the ticketService object into the
    // TicketController
    // class.
    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * This function takes a TicketRequestDTO object as a parameter, and returns a
     * ResponseEntity
     * object.
     * 
     * @param ticketRequestDTO This is the object that will be sent to the server.
     * @return ResponseEntity&lt;ApiResponse&gt;
     */
    @PostMapping
    public ResponseEntity<ApiResponse> save(@RequestBody final TicketRequestDTO ticketRequestDTO) {
        return ticketService.saveTicket(ticketRequestDTO);
    }

    /**
     * It takes a ticket id and a receipt file and sets the receipt for the ticket
     * 
     * @param id      The id of the ticket
     * @param receipt is the file that is being uploaded
     * @return A ResponseEntity with a body of type ApiResponse.
     */
    @PutMapping
    public ResponseEntity<ApiResponse> setReciept(
            @RequestParam final String id,
            @RequestParam final MultipartFile receipt) throws IOException {
        return ticketService.setReciept(new TicketReceiptRequestDTO(id, receipt));
    }

    /**
     * This function returns a response entity that contains an api response that
     * contains a list of
     * tickets.
     * 
     * @return ResponseEntity&lt;ApiResponse&gt;
     */
    @GetMapping
    public ResponseEntity<ApiResponse> findAll() {
        return ticketService.findAll();
    }

    // A function that takes a client id, and returns a response entity with an api
    // response.
    @PutMapping(value = "/verify/{id}")
    public ResponseEntity<ApiResponse> vericyReceipt(@PathVariable String id) throws IOException, WriterException {
        return ticketService.verifyReceipt(id);
    }

    /**
     * It takes a client id, and returns a response entity with an api response
     * 
     * @param id the id of the client
     * @return A ResponseEntity object.
     */
    @GetMapping(value = "/client/{id}")
    public ResponseEntity<ApiResponse> findByClient(@PathVariable String id) {
        return ticketService.findByClient(id);
    }

    /**
     * This function checks if a ticket is valid or not.
     * 
     * @param id The id of the ticket
     * @return A ResponseEntity object.
     */
    @PutMapping(value = "/check/{id}")
    public ResponseEntity<ApiResponse> checkTicket(@PathVariable final String id) {
        return ticketService.checkTicket(id);
    }
}

package com.cdg.buslinkbackend.service.ticket;

import com.cdg.buslinkbackend.model.request.ticket.TicketReceiptRequestDTO;
import com.cdg.buslinkbackend.model.request.ticket.TicketRequestDTO;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import com.google.zxing.WriterException;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface TicketService {

    ResponseEntity<ApiResponse> saveTicket(TicketRequestDTO ticketRequestDTO);

    ResponseEntity<ApiResponse> setReciept(TicketReceiptRequestDTO ticketReceiptRequestDTO) throws IOException;

    ResponseEntity<ApiResponse> findAll();

    ResponseEntity<ApiResponse> verifyReceipt(String id) throws IOException, WriterException;

    ResponseEntity<ApiResponse> findByClient(String idClient);
}

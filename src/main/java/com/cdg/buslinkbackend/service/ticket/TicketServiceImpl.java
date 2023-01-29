package com.cdg.buslinkbackend.service.ticket;

import com.cdg.buslinkbackend.exception.BusNotFoundException;
import com.cdg.buslinkbackend.exception.ClientNotFoundException;
import com.cdg.buslinkbackend.model.entity.Bus;
import com.cdg.buslinkbackend.model.entity.Client;
import com.cdg.buslinkbackend.model.entity.Seating;
import com.cdg.buslinkbackend.model.entity.Ticket;
import com.cdg.buslinkbackend.model.enums.SeatingStatus;
import com.cdg.buslinkbackend.model.enums.TicketStatus;
import com.cdg.buslinkbackend.model.mappers.TicketMapper;
import com.cdg.buslinkbackend.model.request.ticket.TicketRequestDTO;
import com.cdg.buslinkbackend.repository.BusRepository;
import com.cdg.buslinkbackend.repository.ClientRepository;
import com.cdg.buslinkbackend.repository.TicketRepository;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import com.cdg.buslinkbackend.util.response.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    private final BusRepository busRepository;

    private final ResponseBuilder responseBuilder;

    private final ClientRepository clientRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, BusRepository busRepository, ResponseBuilder responseBuilder, ClientRepository clientRepository) {
        this.ticketRepository = ticketRepository;
        this.busRepository = busRepository;
        this.responseBuilder = responseBuilder;
        this.clientRepository = clientRepository;
    }

    @Override
    public ResponseEntity<ApiResponse> saveTicket(TicketRequestDTO ticketRequestDTO) {
        Bus bus = busRepository.findByBusNumber(ticketRequestDTO.getBusNumber()).orElseThrow( () -> new BusNotFoundException(ticketRequestDTO.getBusNumber().toString()));

        Client client = clientRepository.findById(ticketRequestDTO.getClient_id()).orElseThrow( () -> new ClientNotFoundException(ticketRequestDTO.getClient_id()));

        List<Seating> seatingList = bus.getSeating().stream().filter(
                seating -> ticketRequestDTO.getSeatings().contains(seating.getNumber()) && seating.getStatus().equals(SeatingStatus.LIBRE)
        ).peek(seating -> seating.setStatus(SeatingStatus.OCUPADO)).toList();

        if(seatingList.isEmpty() || seatingList.size() != ticketRequestDTO.getSeatings().size()){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "El o los asientos no estan disponibles");
        }
        bus.getSeating().forEach(
                seating -> {
                    if(ticketRequestDTO.getSeatings().contains(seating.getNumber()) && seating.getStatus().equals(SeatingStatus.LIBRE)){
                        seating.setStatus(SeatingStatus.OCUPADO);
                    }
                }
        );
        busRepository.save(bus);
        Ticket ticket = TicketMapper.ticketFromTicketRequestDTO(ticketRequestDTO, seatingList);
        ticket.setStatus(TicketStatus.PENDIENTE);
        ticket.setCreatedAt(new Date());
        ticket = ticketRepository.save(ticket);
        ticket.setClient(client);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Compra exitosa", ticket);
    }
}

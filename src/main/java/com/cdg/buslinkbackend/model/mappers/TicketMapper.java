package com.cdg.buslinkbackend.model.mappers;

import com.cdg.buslinkbackend.model.entity.Seating;
import com.cdg.buslinkbackend.model.entity.Ticket;
import com.cdg.buslinkbackend.model.request.ticket.TicketRequestDTO;

import java.util.List;

public class TicketMapper {

    public static Ticket ticketFromTicketRequestDTO(TicketRequestDTO ticketRequestDTO, List<Seating> seatingList){
        return Ticket.builder()
                .cooperative(ticketRequestDTO.getCooperative())
                .busNumber(ticketRequestDTO.getBusNumber())
                .price(ticketRequestDTO.getPrice())
                .departureTime(ticketRequestDTO.getDepartureTime())
                .origen(ticketRequestDTO.getOrigen())
                .destiny(ticketRequestDTO.getDestiny())
                .seatings(seatingList)
                .build();
    }
}

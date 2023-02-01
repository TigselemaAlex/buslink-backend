package com.cdg.buslinkbackend.model.mappers;

import com.cdg.buslinkbackend.model.entity.Seating;
import com.cdg.buslinkbackend.model.entity.Ticket;
import com.cdg.buslinkbackend.model.request.ticket.TicketRequestDTO;

import java.util.List;

/**
 * It takes a TicketRequestDTO and a list of seatings and returns a Ticket
 */
public class TicketMapper {

    /**
     * It takes a TicketRequestDTO and a list of seatings and returns a Ticket
     * 
     * @param ticketRequestDTO This is the object that I'm sending from the
     *                         frontend.
     * @param seatingList      List of Seating objects
     * @return A ticket object
     */
    public static Ticket ticketFromTicketRequestDTO(TicketRequestDTO ticketRequestDTO, List<Seating> seatingList) {
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

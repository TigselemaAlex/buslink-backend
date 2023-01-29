package com.cdg.buslinkbackend.model.request.ticket;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TicketRequestDTO {

    private String cooperative;
    private Integer busNumber;
    private String client_id;
    private Double price;
    private String departureTime;
    private String origen;
    private String destiny;
    private List<Integer> seatings = new ArrayList<>();
}

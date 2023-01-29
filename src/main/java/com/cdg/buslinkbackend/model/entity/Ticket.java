package com.cdg.buslinkbackend.model.entity;

import com.cdg.buslinkbackend.model.enums.TicketStatus;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document
@Builder
public class Ticket {
    @Id
    private String id;
    @DocumentReference
    private Client client;
    private Date createdAt;
    private String cooperative;
    private Integer busNumber;
    private Double price;
    private String departureTime;
    private String origen;
    private String destiny;
    private List<Seating> seatings = new ArrayList<>();
    @Field(targetType = FieldType.STRING)
    private TicketStatus status;
    private byte[] receipt;

}

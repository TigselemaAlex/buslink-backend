package com.cdg.buslinkbackend.model.request.ticket;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * This class is a DTO that represents a request to upload a receipt for a
 * ticket.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketReceiptRequestDTO {

    private String id;
    private MultipartFile receipt;
}

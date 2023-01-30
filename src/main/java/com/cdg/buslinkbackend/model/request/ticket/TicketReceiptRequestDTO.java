package com.cdg.buslinkbackend.model.request.ticket;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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

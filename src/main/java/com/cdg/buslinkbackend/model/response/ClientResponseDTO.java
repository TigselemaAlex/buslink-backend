package com.cdg.buslinkbackend.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDTO {

    private String id;

    private String ci;

    private String full_name;

    private String phone;

    private String city;

    private String email;

    private String role;

    private boolean status;
}

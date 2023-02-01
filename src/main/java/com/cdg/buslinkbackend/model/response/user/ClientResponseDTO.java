package com.cdg.buslinkbackend.model.response.user;

import lombok.*;

/**
 * This class is a DTO (Data Transfer Object) that represents the response of a
 * client
 */
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

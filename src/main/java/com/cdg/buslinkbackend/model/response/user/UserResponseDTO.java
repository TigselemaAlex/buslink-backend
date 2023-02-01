package com.cdg.buslinkbackend.model.response.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * UserResponseDTO is a class that represents a user response data transfer
 * object
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@ToString
public class UserResponseDTO {
    private String id;

    private String ci;

    private String full_name;

    private String phone;

    private String city;

    private boolean status;

    private String username;

    private String role;
}

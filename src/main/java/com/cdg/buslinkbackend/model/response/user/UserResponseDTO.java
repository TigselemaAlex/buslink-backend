package com.cdg.buslinkbackend.model.response.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

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

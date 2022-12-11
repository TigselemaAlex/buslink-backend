package com.cdg.buslinkbackend.model.request;


import com.cdg.buslinkbackend.model.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserRequestDTO {
    private String ci;

    private String full_name;

    private String phone;

    private String city;

    private boolean status;

    private String username;

    private String password;

    private Role role;

}

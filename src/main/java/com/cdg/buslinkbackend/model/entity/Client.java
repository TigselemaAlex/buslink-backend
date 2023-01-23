package com.cdg.buslinkbackend.model.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clients")
public class Client {
    @Id
    private String id;

    private String ci;

    private String full_name;

    private String phone;

    private String city;

    private String email;

    private String password;

    private String role;

    private boolean status;
}

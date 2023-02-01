package com.cdg.buslinkbackend.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User is a class that represents a user in the system
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String ci;

    private String full_name;

    private String phone;

    private String city;

    private boolean status;

    private String username;

    private String password;

    private String role;

    @DBRef
    private Cooperative cooperative;

}

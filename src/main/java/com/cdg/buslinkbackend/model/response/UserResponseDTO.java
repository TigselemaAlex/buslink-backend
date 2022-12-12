package com.cdg.buslinkbackend.model.response;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.cdg.buslinkbackend.model.entity.Role;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
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

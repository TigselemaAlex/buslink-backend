package com.cdg.buslinkbackend.model.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@DynamoDBTable( tableName = "Users")
public class User {

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String id;

    @DynamoDBAttribute
    private String ci;

    @DynamoDBAttribute
    private String full_name;

    @DynamoDBAttribute
    private String phone;

    @DynamoDBAttribute
    private String city;

    @DynamoDBAttribute
    private boolean status;

    @DynamoDBAttribute
    private String username;

    @DynamoDBAttribute
    private String password;

    @DynamoDBAttribute
    private String role;

    @DynamoDBAttribute
    private String coop_id;


}

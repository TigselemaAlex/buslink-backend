package com.cdg.buslinkbackend.model.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.cdg.buslinkbackend.model.enums.RoleType;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@DynamoDBTable(tableName = "Roles")
public class Role {

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String id;

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute
    private RoleType name;

    public Role(RoleType name){
        this.name = name;
    }


}

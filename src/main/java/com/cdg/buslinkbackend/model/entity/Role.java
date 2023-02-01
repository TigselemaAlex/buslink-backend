package com.cdg.buslinkbackend.model.entity;

import com.cdg.buslinkbackend.model.enums.RoleType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

/**
 * The Role class is a POJO that represents a role in the system
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Document(collection = "roles")
public class Role {

    @Id
    private String id;

    @Field(targetType = FieldType.STRING)
    private RoleType name;

    // A constructor.
    public Role(RoleType name) {
        this.name = name;
    }

}

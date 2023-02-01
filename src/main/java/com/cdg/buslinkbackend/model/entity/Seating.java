package com.cdg.buslinkbackend.model.entity;

import com.cdg.buslinkbackend.model.enums.SeatingStatus;
import com.cdg.buslinkbackend.model.enums.SeatingType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

/**
 * The Seating class has a number, type and status
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Seating {
    private Integer number;
    @Field(targetType = FieldType.STRING)
    private SeatingType type;

    private SeatingStatus status;
}

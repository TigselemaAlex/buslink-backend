package com.cdg.buslinkbackend.model.entity;

import com.cdg.buslinkbackend.model.enums.SeatingType;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Seating {
    private Integer number;
    @Field(targetType = FieldType.STRING)
    private SeatingType seatingType;
}

package com.cdg.buslinkbackend.model.entity;

import com.cdg.buslinkbackend.model.enums.FrequencyType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.List;

/**
 * Frequency is a class that has a list of stops, and a list of stops is a list
 * of strings
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Document(collection = "frequencies")
public class Frequency {
    @Id
    private String id;

    private String origen;

    private String destiny;

    @Field(targetType = FieldType.STRING)
    private FrequencyType type;

    private Integer hours;

    private Integer minutes;

    private Double price;

    List<String> stops = new ArrayList<>();

}

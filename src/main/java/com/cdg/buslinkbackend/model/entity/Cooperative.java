package com.cdg.buslinkbackend.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Document(collection = "cooperatives")
public class Cooperative {
    @Id
    private String id;

    private String name;

    private Boolean status;

    private String phone;

    private String address;

    private Integer max;

    private byte[] image;

    @DocumentReference
    List<Frequency> frequencies = new ArrayList<>();

}

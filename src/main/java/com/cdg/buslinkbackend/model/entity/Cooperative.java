package com.cdg.buslinkbackend.model.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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

    @DBRef
    List<Frequency> frequencies = new ArrayList<>();

}

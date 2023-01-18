package com.cdg.buslinkbackend.model.response.cooperative;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CooperativeResponseDTO {

    private String id;

    private String name;

    private Boolean status;

    private String phone;

    private String address;

    private Integer max;
    private byte[] image;
}

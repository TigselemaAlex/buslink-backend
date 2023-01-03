package com.cdg.buslinkbackend.model.request.cooperative;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class CooperativeRequestDTO {

    private String name;

    private String phone;

    private String address;

    private MultipartFile image;
}

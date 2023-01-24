package com.cdg.buslinkbackend.model.mappers;

import com.cdg.buslinkbackend.model.entity.Cooperative;
import com.cdg.buslinkbackend.model.request.cooperative.CooperativeRequestDTO;
import com.cdg.buslinkbackend.model.response.cooperative.CooperativeResponseDTO;
import com.cdg.buslinkbackend.util.compressor.ImageCompressor;

import java.io.IOException;

public class CooperativeMapper {

    public static CooperativeResponseDTO cooperativeResponseDTOFromCooperative(Cooperative cooperative){
        return CooperativeResponseDTO.builder()
                .id(cooperative.getId())
                .name(cooperative.getName())
                .phone(cooperative.getPhone())
                .status(cooperative.getStatus())
                .max(cooperative.getMax())
                .image(cooperative.getImage()!= null ? ImageCompressor.decompressZLib(cooperative.getImage()) : null )
                .address(cooperative.getAddress())
                .build();
    }

    public static Cooperative cooperativeFromCooperativeRequestDTO(CooperativeRequestDTO cooperativeRequestDTO) throws IOException {
        return Cooperative.builder()
                .name(cooperativeRequestDTO.getName())
                .address(cooperativeRequestDTO.getAddress())
                .phone(cooperativeRequestDTO.getPhone())
                .max(cooperativeRequestDTO.getMax())
                .status(cooperativeRequestDTO.getStatus())
                .image(cooperativeRequestDTO.getImage()!= null ? ImageCompressor.compressZLib(cooperativeRequestDTO.getImage().getBytes()): null)
                .build();
    }
}

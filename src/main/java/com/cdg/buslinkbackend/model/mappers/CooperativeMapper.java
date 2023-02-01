package com.cdg.buslinkbackend.model.mappers;

import com.cdg.buslinkbackend.model.entity.Cooperative;
import com.cdg.buslinkbackend.model.request.cooperative.CooperativeRequestDTO;
import com.cdg.buslinkbackend.model.response.cooperative.CooperativeResponseDTO;
import com.cdg.buslinkbackend.util.compressor.ImageCompressor;

import java.io.IOException;

/**
 * It takes a Cooperative object, and returns a CooperativeResponseDTO object
 */
public class CooperativeMapper {

    /**
     * It takes a Cooperative object, and returns a CooperativeResponseDTO object
     * 
     * @param cooperative the object that I want to map
     * @return A CooperativeResponseDTO object
     */
    public static CooperativeResponseDTO cooperativeResponseDTOFromCooperative(Cooperative cooperative) {
        return CooperativeResponseDTO.builder()
                .id(cooperative.getId())
                .name(cooperative.getName())
                .phone(cooperative.getPhone())
                .status(cooperative.getStatus())
                .max(cooperative.getMax())
                .frequencies(cooperative.getFrequencies() != null ? cooperative.getFrequencies().stream().map(
                        FrequencyMapper::frequencyResponseDTOFromFrequency).toList() : null)
                .image(cooperative.getImage() != null ? ImageCompressor.decompressZLib(cooperative.getImage()) : null)
                .address(cooperative.getAddress())
                .build();
    }

    /**
     * It takes a CooperativeRequestDTO object, and returns a Cooperative object
     * 
     * @param cooperativeRequestDTO The object that contains the image in the form
     *                              of a byte array.
     * @return A Cooperative object
     */
    public static Cooperative cooperativeFromCooperativeRequestDTO(CooperativeRequestDTO cooperativeRequestDTO)
            throws IOException {
        return Cooperative.builder()
                .name(cooperativeRequestDTO.getName())
                .address(cooperativeRequestDTO.getAddress())
                .phone(cooperativeRequestDTO.getPhone())
                .max(cooperativeRequestDTO.getMax())
                .status(cooperativeRequestDTO.getStatus())
                .image(cooperativeRequestDTO.getImage() != null
                        ? ImageCompressor.compressZLib(cooperativeRequestDTO.getImage().getBytes())
                        : null)
                .build();
    }

}

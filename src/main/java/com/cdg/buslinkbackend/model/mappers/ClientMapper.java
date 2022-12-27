package com.cdg.buslinkbackend.model.mappers;

import com.cdg.buslinkbackend.model.entity.Client;
import com.cdg.buslinkbackend.model.request.ClientRegisterRequestDTO;
import com.cdg.buslinkbackend.model.response.ClientResponseDTO;

public class ClientMapper {
    public static Client clientFromClientRegisterRequestDTO(ClientRegisterRequestDTO clientRegisterRequestDTO){
        return Client.builder()
                .ci(clientRegisterRequestDTO.getCi())
                .email(clientRegisterRequestDTO.getEmail())
                .city(clientRegisterRequestDTO.getCity())
                .phone(clientRegisterRequestDTO.getPhone())
                .full_name(clientRegisterRequestDTO.getFull_name())
                .password(clientRegisterRequestDTO.getPassword())
                .build();
    }

    public static ClientResponseDTO clientResponseDTOFromClient(Client client){
        return ClientResponseDTO.builder()
                .id(client.getId())
                .role(client.getRole())
                .status(client.isStatus())
                .email(client.getEmail())
                .phone(client.getPhone())
                .city(client.getCity())
                .full_name(client.getFull_name())
                .build();
    }
}

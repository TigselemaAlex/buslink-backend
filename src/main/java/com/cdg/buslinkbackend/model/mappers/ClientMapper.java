package com.cdg.buslinkbackend.model.mappers;

import com.cdg.buslinkbackend.model.entity.Client;
import com.cdg.buslinkbackend.model.request.user.ClientRegisterRequestDTO;
import com.cdg.buslinkbackend.model.response.user.ClientResponseDTO;
import com.cdg.buslinkbackend.security.model.UserPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

/**
 * It takes a ClientRegisterRequestDTO object and returns a Client object
 */

public class ClientMapper {
    /**
     * It takes a ClientRegisterRequestDTO object and returns a Client object
     * 
     * @param clientRegisterRequestDTO
     * @return A Client object
     */
    public static Client clientFromClientRegisterRequestDTO(ClientRegisterRequestDTO clientRegisterRequestDTO) {
        return Client.builder()
                .ci(clientRegisterRequestDTO.getCi())
                .email(clientRegisterRequestDTO.getEmail())
                .city(clientRegisterRequestDTO.getCity())
                .phone(clientRegisterRequestDTO.getPhone())
                .full_name(clientRegisterRequestDTO.getFull_name())
                .password(clientRegisterRequestDTO.getPassword())
                .build();
    }

    /**
     * It takes a Client object and returns a ClientResponseDTO object
     * 
     * @param client the object that is going to be converted
     * @return A ClientResponseDTO object
     */
    public static ClientResponseDTO clientResponseDTOFromClient(Client client) {
        return ClientResponseDTO.builder()
                .id(client.getId())
                .role(client.getRole())
                .status(client.isStatus())
                .email(client.getEmail())
                .phone(client.getPhone())
                .city(client.getCity())
                .full_name(client.getFull_name())
                .ci(client.getCi())
                .build();
    }

    /**
     * It takes a user object and returns a user principal object
     * 
     * @param client the client object that is returned from the database
     * @return A UserPrincipal object
     */
    public static UserPrincipal userPrincipalFromUser(Client client) {
        List<SimpleGrantedAuthority> authorities = Collections
                .singletonList(new SimpleGrantedAuthority(client.getRole()));
        return UserPrincipal.builder()
                .username(client.getEmail())
                .password(client.getPassword())
                .status(client.isStatus())
                .authorities(authorities)
                .build();
    }
}

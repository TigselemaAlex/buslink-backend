package com.cdg.buslinkbackend.service.user;

import com.cdg.buslinkbackend.model.entity.Client;
import com.cdg.buslinkbackend.model.enums.RoleType;
import com.cdg.buslinkbackend.model.mappers.ClientMapper;
import com.cdg.buslinkbackend.model.request.ClientRegisterRequestDTO;
import com.cdg.buslinkbackend.model.response.ClientResponseDTO;
import com.cdg.buslinkbackend.repository.ClientRepository;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import com.cdg.buslinkbackend.util.response.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ResponseBuilder responseBuilder;

    public ResponseEntity<ApiResponse> registerClient(ClientRegisterRequestDTO clientRegisterRequestDTO){
        if(clientRepository.existsByEmail(clientRegisterRequestDTO.getEmail())){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "Ya existe una cuenta asosiada a ese email.");
        }
        Client client = ClientMapper.clientFromClientRegisterRequestDTO(clientRegisterRequestDTO);
        client.setRole(RoleType.USER.name());
        client.setStatus(true);
        client = clientRepository.save(client);
        ClientResponseDTO clientResponseDTO = ClientMapper.clientResponseDTOFromClient(client);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Registro exitoso", clientResponseDTO);
    }
}

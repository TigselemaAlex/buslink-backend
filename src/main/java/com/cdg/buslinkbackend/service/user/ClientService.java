package com.cdg.buslinkbackend.service.user;

import com.cdg.buslinkbackend.exception.ClientNotFoundException;
import com.cdg.buslinkbackend.model.entity.Client;
import com.cdg.buslinkbackend.model.enums.RoleType;
import com.cdg.buslinkbackend.model.mappers.ClientMapper;
import com.cdg.buslinkbackend.model.request.user.ClientRegisterRequestDTO;
import com.cdg.buslinkbackend.model.response.user.ClientResponseDTO;
import com.cdg.buslinkbackend.repository.ClientRepository;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import com.cdg.buslinkbackend.util.response.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * It receives a ClientRegisterRequestDTO object, checks if the email or ci
 * already exists in the
 * database, if not, it creates a new Client object, sets the role, status, and
 * password, and saves it
 * to the database
 */
@Service
@Transactional
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ResponseBuilder responseBuilder;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * It receives a ClientRegisterRequestDTO object, checks if the email or ci
     * already exists in the
     * database, if not, it creates a new Client object, sets the role, status, and
     * password, and saves
     * it to the database
     * 
     * @param clientRegisterRequestDTO This is the object that contains the data
     *                                 that the user sends to
     *                                 the server.
     * @return A ResponseEntity<ApiResponse>
     */
    public ResponseEntity<ApiResponse> registerClient(ClientRegisterRequestDTO clientRegisterRequestDTO) {
        if (clientRepository.existsByEmail(clientRegisterRequestDTO.getEmail())) {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(),
                    "Ya existe una cuenta asociada a ese email.");
        }
        if (clientRepository.existsByCi(clientRegisterRequestDTO.getCi())) {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(),
                    "Ya existe un usuario con esa cedula.");
        }
        Client client = ClientMapper.clientFromClientRegisterRequestDTO(clientRegisterRequestDTO);
        client.setRole(RoleType.USER.name());
        client.setStatus(true);
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        client = clientRepository.save(client);
        ClientResponseDTO clientResponseDTO = ClientMapper.clientResponseDTOFromClient(client);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Registro exitoso", clientResponseDTO);
    }

    /**
     * It finds a client by email and returns a response with the client's data
     * 
     * @param email String
     * @return A ResponseEntity<ApiResponse>
     */
    public ResponseEntity<ApiResponse> findByEmail(String email) {
        Client client = clientRepository.findByEmail(email).orElseThrow(() -> new ClientNotFoundException(email));
        ClientResponseDTO clientResponseDTO = ClientMapper.clientResponseDTOFromClient(client);
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Cliente encontrado exitosamente",
                clientResponseDTO);
    }

    /**
     * It finds a client by cedula, if it doesn't find it, it throws an exception,
     * if it finds it, it
     * maps the client to a DTO and returns a response
     * 
     * @param ci String
     * @return A ResponseEntity<ApiResponse>
     */
    public ResponseEntity<ApiResponse> findByCedula(String ci) {
        Client client = clientRepository.findByCi(ci).orElseThrow(() -> new ClientNotFoundException(ci));
        ClientResponseDTO clientResponseDTO = ClientMapper.clientResponseDTOFromClient(client);
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Cliente encontrado exitosamente",
                clientResponseDTO);
    }
}

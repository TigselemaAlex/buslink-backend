package com.cdg.buslinkbackend.service.cooperative;

import com.cdg.buslinkbackend.exception.CooperativeNotFoundException;
import com.cdg.buslinkbackend.exception.FrequencyNotFoundException;
import com.cdg.buslinkbackend.model.entity.Cooperative;
import com.cdg.buslinkbackend.model.entity.Frequency;
import com.cdg.buslinkbackend.model.mappers.CooperativeMapper;
import com.cdg.buslinkbackend.model.request.cooperative.CooperativeRequestDTO;
import com.cdg.buslinkbackend.model.request.cooperative.CooperativeWithFrequenciesRequestDTO;
import com.cdg.buslinkbackend.model.response.cooperative.CooperativeResponseDTO;
import com.cdg.buslinkbackend.repository.CooperativeRepository;
import com.cdg.buslinkbackend.repository.FrequencyRespository;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import com.cdg.buslinkbackend.util.response.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * This class is a service that implements the interface ICooperativeService,
 * which has the methods to
 * create, update, delete, and list cooperatives
 */
@Service
@Transactional
public class CooperativeSeriveImpl implements ICooperativeService {

    private final CooperativeRepository cooperativeRepository;

    private final FrequencyRespository frequencyRespository;

    private final ResponseBuilder responseBuilder;

    // A constructor that is injecting the dependencies of the class.
    @Autowired
    public CooperativeSeriveImpl(CooperativeRepository cooperativeRepository, FrequencyRespository frequencyRespository,
            ResponseBuilder responseBuilder) {
        this.cooperativeRepository = cooperativeRepository;
        this.frequencyRespository = frequencyRespository;
        this.responseBuilder = responseBuilder;
    }

    /**
     * It returns a list of cooperatives, if there are no cooperatives it returns a
     * message saying
     * there are no cooperatives, if there are cooperatives it returns a list of
     * cooperatives
     * 
     * @return ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> findAll() {
        List<Cooperative> cooperatives = (List<Cooperative>) cooperativeRepository.findAll();
        if (cooperatives.isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.NO_CONTENT.value(), "No hay cooperativas que mostrar");
        }
        List<CooperativeResponseDTO> cooperativeResponseDTOS = cooperatives.stream()
                .map(CooperativeMapper::cooperativeResponseDTOFromCooperative).toList();
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Listado de cooperativas", cooperativeResponseDTOS);
    }

    /**
     * It finds a cooperative by id and returns a response entity with the
     * cooperative found
     * 
     * @param id String
     * @return A ResponseEntity with a body of type ApiResponse.
     */
    @Override
    public ResponseEntity<ApiResponse> findById(String id) {
        Cooperative cooperative = cooperativeRepository.findById(id)
                .orElseThrow(() -> new CooperativeNotFoundException(id));
        CooperativeResponseDTO cooperativeResponseDTO = CooperativeMapper
                .cooperativeResponseDTOFromCooperative(cooperative);
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Cooperativa encontrada", cooperativeResponseDTO);
    }

    /**
     * The function is called save and it takes a CooperativeRequestDTO as a
     * parameter. It returns a
     * ResponseEntity<ApiResponse> and throws an IOException
     * 
     * @param cooperativeRequestDTO This is the object that is sent from the
     *                              frontend.
     * @return ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> save(CooperativeRequestDTO cooperativeRequestDTO) throws IOException {
        if (cooperativeRepository.existsByName(cooperativeRequestDTO.getName())) {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(),
                    "La cooperativa con ese nombre ya existe");
        }
        Cooperative cooperative = CooperativeMapper.cooperativeFromCooperativeRequestDTO(cooperativeRequestDTO);
        cooperative.setStatus(true);
        cooperative = cooperativeRepository.save(cooperative);
        CooperativeResponseDTO cooperativeResponseDTO = CooperativeMapper
                .cooperativeResponseDTOFromCooperative(cooperative);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Cooperativa guardada exitosamente!!!",
                cooperativeResponseDTO);
    }

    /**
     * The function receives a request with a JSON body, and then updates the
     * database with the new
     * values
     * 
     * @param id                    The id of the cooperative to be updated
     * @param cooperativeRequestDTO This is the object that contains the data that I
     *                              want to update.
     * @return A ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> update(String id, CooperativeRequestDTO cooperativeRequestDTO)
            throws IOException {
        Cooperative cooperativeToSave = cooperativeRepository.findById(id)
                .orElseThrow(() -> new CooperativeNotFoundException(id));
        Cooperative cooperativeRequest = CooperativeMapper.cooperativeFromCooperativeRequestDTO(cooperativeRequestDTO);
        cooperativeToSave.setName(cooperativeRequest.getName());
        cooperativeToSave.setAddress(cooperativeRequest.getAddress());
        cooperativeToSave.setPhone(cooperativeRequest.getPhone());
        cooperativeToSave.setMax(cooperativeRequest.getMax());
        cooperativeToSave.setStatus(cooperativeRequest.getStatus());
        if (Objects.nonNull(cooperativeRequest.getImage())) {
            cooperativeToSave.setImage(cooperativeRequest.getImage());
        }
        Cooperative cooperative = cooperativeRepository.save(cooperativeToSave);
        CooperativeResponseDTO cooperativeResponseDTO = CooperativeMapper
                .cooperativeResponseDTOFromCooperative(cooperative);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Cooperativa actualizada exitosamente!!!",
                cooperativeResponseDTO);
    }

    /**
     * If the cooperative exists, delete it and return a response with a 200 status
     * code and a message.
     * If it doesn't exist, return a response with a 400 status code and a message
     * 
     * @param id The id of the cooperative to be deleted
     * @return ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> delete(String id) {
        if (cooperativeRepository.existsById(id)) {
            cooperativeRepository.deleteById(id);
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Cooperativa eliminada exitosamente!!!");
        }
        return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "Cooperativa no encontrada");
    }

    /**
     * I'm trying to save a list of frequencies to a cooperative
     * 
     * @param cooperativeWithFrequenciesRequestDTO
     * @return A ResponseEntity with a body of type ApiResponse.
     */
    @Override
    public ResponseEntity<ApiResponse> saveFrequencies(
            CooperativeWithFrequenciesRequestDTO cooperativeWithFrequenciesRequestDTO) {

        Cooperative cooperative = cooperativeRepository
                .findById(cooperativeWithFrequenciesRequestDTO.getCooperative_id())
                .orElseThrow(() -> new CooperativeNotFoundException(
                        cooperativeWithFrequenciesRequestDTO.getCooperative_id()));
        List<Frequency> frequencies = cooperativeWithFrequenciesRequestDTO.getFrequencies_ids().stream().map(
                id -> frequencyRespository.findById(id).orElseThrow(() -> new FrequencyNotFoundException(id))).toList();
        cooperative.setFrequencies(frequencies);
        cooperative = cooperativeRepository.save(cooperative);
        CooperativeResponseDTO cooperativeWithFrequenciesResponseDTO = CooperativeMapper
                .cooperativeResponseDTOFromCooperative(cooperative);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(),
                "Listado de frecuencias actualizado exitosamente", cooperativeWithFrequenciesResponseDTO);

    }

}

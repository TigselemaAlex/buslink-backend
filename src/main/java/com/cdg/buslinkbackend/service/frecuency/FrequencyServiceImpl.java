package com.cdg.buslinkbackend.service.frecuency;

import com.cdg.buslinkbackend.exception.FrequencyNotFoundException;
import com.cdg.buslinkbackend.model.entity.Frequency;
import com.cdg.buslinkbackend.model.mappers.FrequencyMapper;
import com.cdg.buslinkbackend.model.request.frequency.FrequencyRequestDTO;
import com.cdg.buslinkbackend.model.response.frecuency.FrequencyResponseDTO;
import com.cdg.buslinkbackend.repository.FrequencyRespository;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import com.cdg.buslinkbackend.util.response.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FrequencyServiceImpl implements FrequencyService {

    private final FrequencyRespository frequencyRespository;
    private final ResponseBuilder responseBuilder;

    // A constructor injection.
    @Autowired
    public FrequencyServiceImpl(FrequencyRespository frequencyRespository, ResponseBuilder responseBuilder) {
        this.frequencyRespository = frequencyRespository;
        this.responseBuilder = responseBuilder;
    }

    /**
     * It returns a ResponseEntity of ApiResponse, which is a class that contains a
     * status code, a
     * message, and a list of FrequencyResponseDTOs
     * 
     * @return A ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> findAll() {
        List<Frequency> frequencies = frequencyRespository.findAll();
        if (frequencies.isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.NO_CONTENT.value(), "No hay frecuencias que mostrar");
        }
        List<FrequencyResponseDTO> frequencyResponseDTOS = frequencies
                .stream().map(FrequencyMapper::frequencyResponseDTOFromFrequency).toList();
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Listado de frecuencias", frequencyResponseDTOS);

    }

    /**
     * It finds a frequency by id, if it doesn't exist it throws an exception, if it
     * does exist it maps
     * it to a DTO and returns a response
     * 
     * @param id String
     * @return A ResponseEntity with a body of type ApiResponse.
     */
    @Override
    public ResponseEntity<ApiResponse> findById(String id) {
        Frequency frequency = frequencyRespository.findById(id).orElseThrow(() -> new FrequencyNotFoundException(id));
        FrequencyResponseDTO frequencyResponseDTO = FrequencyMapper.frequencyResponseDTOFromFrequency(frequency);
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Frecuencia encontrada", frequencyResponseDTO);
    }

    /**
     * If the frequency already exists, return a bad request response, otherwise
     * save the frequency and
     * return a created response
     * 
     * @param frequencyRequestDTO This is the object that is sent from the frontend.
     * @return A ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> save(FrequencyRequestDTO frequencyRequestDTO) {
        if (findByDestinyAndOrigenAndStopsAndType(frequencyRequestDTO))
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "Ya existe la frecuencia");
        Frequency frequencyToSave = FrequencyMapper.frequencyFromFrequencyRequestDTO(frequencyRequestDTO);
        frequencyToSave = frequencyRespository.save(frequencyToSave);
        FrequencyResponseDTO frequencyResponseDTO = FrequencyMapper.frequencyResponseDTOFromFrequency(frequencyToSave);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Frecuencia guardada exitosamente",
                frequencyResponseDTO);
    }

    /**
     * It updates a frequency in the database
     * 
     * @param id                  String
     * @param frequencyRequestDTO This is the object that contains the data that
     *                            will be updated.
     * @return ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> update(String id, FrequencyRequestDTO frequencyRequestDTO) {
        Optional<Frequency> optionalFrequency = frequencyRespository
                .findByDestinyAndOrigenAndStopsAndPriceAndHoursAndMinutesAndType(
                        frequencyRequestDTO.getDestiny(), frequencyRequestDTO.getOrigen(),
                        frequencyRequestDTO.getStops(),
                        frequencyRequestDTO.getPrice(), frequencyRequestDTO.getHours(),
                        frequencyRequestDTO.getMinutes(), frequencyRequestDTO.getType());

        if (optionalFrequency.isPresent()) {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "La frecuencia ya existe");
        }

        Frequency frequencyToUpdate = frequencyRespository.findById(id)
                .orElseThrow(() -> new FrequencyNotFoundException(id));
        frequencyToUpdate.setType(frequencyRequestDTO.getType());
        frequencyToUpdate.setOrigen(frequencyRequestDTO.getOrigen());
        frequencyToUpdate.setDestiny(frequencyRequestDTO.getDestiny());
        frequencyToUpdate.setPrice(frequencyRequestDTO.getPrice());
        frequencyToUpdate.setStops(frequencyRequestDTO.getStops());
        frequencyToUpdate.setHours(frequencyRequestDTO.getHours());
        frequencyToUpdate.setMinutes(frequencyRequestDTO.getMinutes());
        frequencyToUpdate = frequencyRespository.save(frequencyToUpdate);
        FrequencyResponseDTO frequencyResponseDTO = FrequencyMapper
                .frequencyResponseDTOFromFrequency(frequencyToUpdate);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Frecuencia actualizada exitosamente",
                frequencyResponseDTO);
    }

    /**
     * If the frequency exists, delete it and return a successful response,
     * otherwise return a bad
     * request response
     * 
     * @param id String
     * @return ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> delete(String id) {
        if (frequencyRespository.existsById(id)) {
            frequencyRespository.deleteById(id);
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Frecuencia eliminada exitosamente");
        }
        return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "Frecuencia no encontrada");
    }

    /**
     * It checks if a frequency exists in the database with the same destiny,
     * origen, stops and type
     * 
     * @param frequencyRequestDTO
     * @return A boolean value.
     */
    private boolean findByDestinyAndOrigenAndStopsAndType(FrequencyRequestDTO frequencyRequestDTO) {
        Optional<Frequency> optionalFrequency = frequencyRespository.findByDestinyAndOrigenAndStopsAndType(
                frequencyRequestDTO.getDestiny(),
                frequencyRequestDTO.getOrigen(),
                frequencyRequestDTO.getStops(),
                frequencyRequestDTO.getType());
        return optionalFrequency.isPresent();
    }
}

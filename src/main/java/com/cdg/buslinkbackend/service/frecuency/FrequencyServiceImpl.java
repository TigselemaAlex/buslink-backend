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
public class FrequencyServiceImpl implements FrequencyService  {

    private final FrequencyRespository frequencyRespository;
    private final ResponseBuilder responseBuilder;

    @Autowired
    public FrequencyServiceImpl(FrequencyRespository frequencyRespository, ResponseBuilder responseBuilder) {
        this.frequencyRespository = frequencyRespository;
        this.responseBuilder = responseBuilder;
    }

    @Override
    public ResponseEntity<ApiResponse> findAll() {
        List<Frequency> frequencies = frequencyRespository.findAll();
        if(frequencies.isEmpty()){
            return responseBuilder.buildResponse(HttpStatus.NO_CONTENT.value(), "No hay frecuencias que mostrar");
        }
        List<FrequencyResponseDTO> frequencyResponseDTOS = frequencies
                .stream().map(FrequencyMapper::frequencyResponseDTOFromFrequency).toList();
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Listado de frecuencias", frequencyResponseDTOS);

    }

    @Override
    public ResponseEntity<ApiResponse> findById(String id) {
        Frequency frequency = frequencyRespository.findById(id).orElseThrow(() -> new FrequencyNotFoundException(id));
        FrequencyResponseDTO  frequencyResponseDTO = FrequencyMapper.frequencyResponseDTOFromFrequency(frequency);
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Frecuencia encontrada", frequencyResponseDTO);
    }

    @Override
    public ResponseEntity<ApiResponse> save(FrequencyRequestDTO frequencyRequestDTO) {
        if (findByDestinyAndOrigenAndStops(frequencyRequestDTO))
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "Ya existe la frecuencia");
        Frequency  frequencyToSave = FrequencyMapper.frequencyFromFrequencyRequestDTO(frequencyRequestDTO);
        frequencyToSave = frequencyRespository.save(frequencyToSave);
        FrequencyResponseDTO frequencyResponseDTO = FrequencyMapper.frequencyResponseDTOFromFrequency(frequencyToSave);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Frecuencia guardada exitosamente", frequencyResponseDTO);
    }




    @Override
    public ResponseEntity<ApiResponse> update(String id, FrequencyRequestDTO frequencyRequestDTO) {
        Optional<Frequency> optionalFrequency = frequencyRespository.findByDestinyAndOrigenAndStopsAndPriceAndHoursAndMinutesAndType(
                frequencyRequestDTO.getDestiny(), frequencyRequestDTO.getOrigen(), frequencyRequestDTO.getStops(),
                frequencyRequestDTO.getPrice(), frequencyRequestDTO.getHours(), frequencyRequestDTO.getMinutes(), frequencyRequestDTO.getType()
        );

        if(optionalFrequency.isPresent()){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "La frecuencia ya existe");
        }

        Frequency frequencyToUpdate = frequencyRespository.findById(id).orElseThrow(()-> new FrequencyNotFoundException(id));
        frequencyToUpdate.setType(frequencyRequestDTO.getType());
        frequencyToUpdate.setOrigen(frequencyRequestDTO.getOrigen());
        frequencyToUpdate.setDestiny(frequencyRequestDTO.getDestiny());
        frequencyToUpdate.setPrice(frequencyRequestDTO.getPrice());
        frequencyToUpdate.setStops(frequencyRequestDTO.getStops());
        frequencyToUpdate.setHours(frequencyRequestDTO.getHours());
        frequencyToUpdate.setMinutes(frequencyRequestDTO.getMinutes());
        frequencyToUpdate = frequencyRespository.save(frequencyToUpdate);
        FrequencyResponseDTO frequencyResponseDTO = FrequencyMapper.frequencyResponseDTOFromFrequency(frequencyToUpdate);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Frecuencia actualizada exitosamente", frequencyResponseDTO);
    }

    @Override
    public ResponseEntity<ApiResponse> delete(String id) {
        if(frequencyRespository.existsById(id)){
            frequencyRespository.deleteById(id);
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Frecuencia eliminada exitosamente");
        }
        return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "Frecuencia no encontrada");
    }

    private boolean findByDestinyAndOrigenAndStops(FrequencyRequestDTO frequencyRequestDTO) {
        Optional<Frequency> optionalFrequency = frequencyRespository.findByDestinyAndOrigenAndStops(
                frequencyRequestDTO.getDestiny(),
                frequencyRequestDTO.getOrigen(),
                frequencyRequestDTO.getStops()
        );
        return optionalFrequency.isPresent();
    }
}

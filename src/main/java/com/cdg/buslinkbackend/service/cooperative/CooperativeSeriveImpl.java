package com.cdg.buslinkbackend.service.cooperative;

import com.cdg.buslinkbackend.exception.CooperativeNotFoundException;
import com.cdg.buslinkbackend.model.entity.Cooperative;
import com.cdg.buslinkbackend.model.mappers.CooperativeMapper;
import com.cdg.buslinkbackend.model.request.cooperative.CooperativeRequestDTO;
import com.cdg.buslinkbackend.model.response.cooperative.CooperativeResponseDTO;
import com.cdg.buslinkbackend.repository.CooperativeRepository;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import com.cdg.buslinkbackend.util.response.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;


@Service
@Transactional
public class CooperativeSeriveImpl implements ICooperativeService{


    private final CooperativeRepository cooperativeRepository;

    private final ResponseBuilder responseBuilder;

    @Autowired
    public CooperativeSeriveImpl(CooperativeRepository cooperativeRepository, ResponseBuilder responseBuilder) {
        this.cooperativeRepository = cooperativeRepository;
        this.responseBuilder = responseBuilder;
    }


    @Override
    public ResponseEntity<ApiResponse> findAll() {
        List<Cooperative> cooperatives = (List<Cooperative>) cooperativeRepository.findAll();
        if (cooperatives.isEmpty()){
            return responseBuilder.buildResponse(HttpStatus.NO_CONTENT.value(), "No hay cooperativas que mostrar");
        }
        List<CooperativeResponseDTO> cooperativeResponseDTOS = cooperatives.stream().map(CooperativeMapper::cooperativeResponseDTOFromCooperative).toList();
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Listado de cooperativas", cooperativeResponseDTOS);
    }

    @Override
    public ResponseEntity<ApiResponse> findById(String id) {
        Cooperative cooperative = cooperativeRepository.findById(id).orElseThrow( () -> new CooperativeNotFoundException(id));
        CooperativeResponseDTO cooperativeResponseDTO = CooperativeMapper.cooperativeResponseDTOFromCooperative(cooperative);
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Cooperativa encontrada", cooperativeResponseDTO);
    }

    @Override
    public ResponseEntity<ApiResponse> save(CooperativeRequestDTO cooperativeRequestDTO) throws IOException {

        Cooperative cooperative = CooperativeMapper.cooperativeFromCooperativeRequestDTO(cooperativeRequestDTO);

        cooperative.setStatus(true);
        cooperative = cooperativeRepository.save(cooperative);
        CooperativeResponseDTO cooperativeResponseDTO =CooperativeMapper.cooperativeResponseDTOFromCooperative(cooperative);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Cooperativa guardada exitosamente!!!", cooperativeResponseDTO);
    }

    @Override
    public ResponseEntity<ApiResponse> update(String id, CooperativeRequestDTO cooperativeRequestDTO) throws IOException {
        Cooperative cooperativeToSave = cooperativeRepository.findById(id).orElseThrow(()-> new CooperativeNotFoundException(id));
        Cooperative cooperativeRequest = CooperativeMapper.cooperativeFromCooperativeRequestDTO(cooperativeRequestDTO);
        cooperativeToSave.setName(cooperativeRequest.getName());
        cooperativeToSave.setAddress(cooperativeRequest.getAddress());
        cooperativeToSave.setPhone(cooperativeRequest.getPhone());
        if(Objects.nonNull(cooperativeRequest.getImage())){
            cooperativeToSave.setImage(cooperativeRequest.getImage());
        }
        Cooperative cooperative = cooperativeRepository.save(cooperativeToSave);
        CooperativeResponseDTO cooperativeResponseDTO = CooperativeMapper.cooperativeResponseDTOFromCooperative(cooperative);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Cooperativa actualizada exitosamente!!!", cooperativeResponseDTO);
    }

    @Override
    public ResponseEntity<ApiResponse> changeStatus(String id) {
        Cooperative cooperativeToChange = cooperativeRepository.findById(id).orElseThrow( () -> new CooperativeNotFoundException(id));
        cooperativeToChange.setStatus(!cooperativeToChange.getStatus());
        Cooperative cooperative = cooperativeRepository.save(cooperativeToChange);
        return responseBuilder.buildResponse(HttpStatus.OK.value(), MessageFormat.format("Cooperativa {0} {1}", cooperative.getName(), cooperative.getStatus() ? "Habilitada": "Deshabilitada"));
    }

    @Override
    public ResponseEntity<ApiResponse> delete(String id) {
        if(cooperativeRepository.existsById(id)){
            cooperativeRepository.deleteById(id);
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Cooperativa eliminada exitosamente!!!");
        }
        return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "Cooperativa no encontrada");
    }
}

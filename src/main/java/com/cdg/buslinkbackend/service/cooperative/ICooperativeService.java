package com.cdg.buslinkbackend.service.cooperative;

import com.cdg.buslinkbackend.model.request.cooperative.CooperativeRequestDTO;
import com.cdg.buslinkbackend.model.request.cooperative.CooperativeWithFrequenciesRequestDTO;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ICooperativeService {

    ResponseEntity<ApiResponse> findAll();
    ResponseEntity<ApiResponse> findById(String id);
    ResponseEntity<ApiResponse> save(CooperativeRequestDTO cooperativeRequestDTO) throws IOException;
    ResponseEntity<ApiResponse> update(String id, CooperativeRequestDTO cooperativeRequestDTO) throws IOException;
    ResponseEntity<ApiResponse> delete(String id);
    ResponseEntity<ApiResponse> saveFrequencies(CooperativeWithFrequenciesRequestDTO cooperativeWithFrequenciesRequestDTO);

}

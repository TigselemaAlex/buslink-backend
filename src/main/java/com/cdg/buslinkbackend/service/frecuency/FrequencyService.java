package com.cdg.buslinkbackend.service.frecuency;

import com.cdg.buslinkbackend.model.request.frequency.FrequencyRequestDTO;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;

// A service interface.
public interface FrequencyService {

    ResponseEntity<ApiResponse> findAll();

    ResponseEntity<ApiResponse> findById(String id);

    ResponseEntity<ApiResponse> save(FrequencyRequestDTO frequencyRequestDTO);

    ResponseEntity<ApiResponse> update(String id, FrequencyRequestDTO frequencyRequestDTO);

    ResponseEntity<ApiResponse> delete(String id);
}

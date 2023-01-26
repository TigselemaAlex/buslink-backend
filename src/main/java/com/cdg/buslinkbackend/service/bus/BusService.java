package com.cdg.buslinkbackend.service.bus;

import com.cdg.buslinkbackend.model.request.bus.BusRequestDTO;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface BusService {
    ResponseEntity<ApiResponse> save(BusRequestDTO  busRequestDTO);
}

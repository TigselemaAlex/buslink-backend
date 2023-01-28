package com.cdg.buslinkbackend.service.bus;

import com.cdg.buslinkbackend.model.request.bus.BusRequestDTO;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface BusService {
    ResponseEntity<ApiResponse> save(BusRequestDTO  busRequestDTO);
    ResponseEntity<ApiResponse> findAllByCooperative(String cooperative_id);
    ResponseEntity<ApiResponse> findById( String id);
    ResponseEntity<ApiResponse> findAllByOrigen(String origen);
    ResponseEntity<ApiResponse> findAllByDestiny(String destiny);
    ResponseEntity<ApiResponse> findAllByOrigenAndDestiny(String origen, String destiny);




}

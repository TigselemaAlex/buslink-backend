package com.cdg.buslinkbackend.service.itinerary;

import com.cdg.buslinkbackend.model.request.itinerary.ItineraryRequestDTO;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface ItineraryService {


    ResponseEntity<ApiResponse> save(ItineraryRequestDTO  itineraryRequestDTO);
    ResponseEntity<ApiResponse> findByOrigenOrDestiny(String origen, String destiny);
}

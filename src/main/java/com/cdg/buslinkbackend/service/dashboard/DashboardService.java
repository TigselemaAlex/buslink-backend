package com.cdg.buslinkbackend.service.dashboard;

import com.cdg.buslinkbackend.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;


public interface DashboardService {

    ResponseEntity<ApiResponse> countUsers();

    ResponseEntity<ApiResponse> countCooperatives();

    ResponseEntity<ApiResponse> countFrequencies();


    ResponseEntity<ApiResponse> countCooperativesWithoutFrequencies();
}

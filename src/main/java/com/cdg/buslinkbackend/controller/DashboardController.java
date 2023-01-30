package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.service.dashboard.DashboardService;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/protected/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping(value = "/users")
     public ResponseEntity<ApiResponse> countUsers() {
        return dashboardService.countUsers();
    }

    @GetMapping(value = "/cooperatives")
    public ResponseEntity<ApiResponse> countCooperatives() {
        return dashboardService.countCooperatives();
    }

    @GetMapping(value = "/frequencies")
    public ResponseEntity<ApiResponse> countFrequencies() {
        return dashboardService.countFrequencies();
    }


    @GetMapping(value = "/cooperatives/without/frequencies")
    public ResponseEntity<ApiResponse> countCooperativesWithoutFrequencies(){
        return dashboardService.countCooperativesWithoutFrequencies();
    }


}

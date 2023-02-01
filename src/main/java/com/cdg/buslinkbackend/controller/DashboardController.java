package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.service.dashboard.DashboardService;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * It's a controller class that contains methods that return response entities
 * with api response
 * objects that contain data for the dashboard.
 */
@RestController
@RequestMapping(value = "/protected/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    // A constructor that takes a DashboardService object as a parameter.
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // A method that returns a response entity with an api response object that
    // contains a list of
    // // cooperatives that don't have frequencies.
    @GetMapping(value = "/users")
    public ResponseEntity<ApiResponse> countUsers() {
        return dashboardService.countUsers();
    }

    // A method that returns a response entity with an api response object that
    // contains a list of
    // // cooperatives that don't have frequencies.
    @GetMapping(value = "/cooperatives")
    public ResponseEntity<ApiResponse> countCooperatives() {
        return dashboardService.countCooperatives();
    }

    // A method that returns a response entity with an api response object that
    // contains a list of
    // cooperatives that don't have frequencies.
    @GetMapping(value = "/frequencies")
    public ResponseEntity<ApiResponse> countFrequencies() {
        return dashboardService.countFrequencies();
    }

    /**
     * It returns a response entity with an api response object that contains a list
     * of cooperatives
     * that don't have frequencies
     * 
     * @return A ResponseEntity<ApiResponse>
     */
    @GetMapping(value = "/cooperatives/without/frequencies")
    public ResponseEntity<ApiResponse> countCooperativesWithoutFrequencies() {
        return dashboardService.countCooperativesWithoutFrequencies();
    }

}

package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.model.request.bus.BusRequestDTO;
import com.cdg.buslinkbackend.service.bus.BusService;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/protected/buses")
public class BusController {

    private final BusService busService;


    @Autowired
    public BusController(BusService busService) {
        this.busService = busService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody final BusRequestDTO busRequestDTO){
        return busService.save(busRequestDTO);
    }

    @GetMapping(value = "/cooperative/{id}")
    public ResponseEntity<ApiResponse> findAllByCooperative(@PathVariable final String id){
        return busService.findAllByCooperative(id);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable final String id){
        return busService.findById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable final String id, @RequestBody final BusRequestDTO busRequestDTO){
        return busService.update(id, busRequestDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable final String id){
        return busService.delete(id);
    }

    @PutMapping(value = "/reset/{id}")
    public ResponseEntity<ApiResponse> resetSeating(@PathVariable final String id){
        return busService.resetSeating(id);
    }

}

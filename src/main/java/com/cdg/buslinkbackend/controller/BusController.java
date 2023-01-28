package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.model.entity.Bus;
import com.cdg.buslinkbackend.model.request.bus.BusRequestDTO;
import com.cdg.buslinkbackend.repository.BusRepository;
import com.cdg.buslinkbackend.service.bus.BusService;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/protected/buses")
public class BusController {

    private final BusService busService;

    @Autowired
    private BusRepository busRepository;

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

    @GetMapping(value = "/cooperative/origen/{name}")
    public List<Bus> findAllByOrigen(@PathVariable final String name){
        return busRepository.findByCooperativeName("Libertadores");
    }
}

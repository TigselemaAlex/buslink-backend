package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.model.request.frequency.FrequencyRequestDTO;
import com.cdg.buslinkbackend.service.frecuency.FrequencyService;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/protected/frequencies")
public class FrequencyController {

    private final FrequencyService frequencyService;

    public FrequencyController(FrequencyService frequencyService) {
        this.frequencyService = frequencyService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAll(){
        return frequencyService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable final String id){
        return frequencyService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody final FrequencyRequestDTO frequencyRequestDTO){
        return frequencyService.save(frequencyRequestDTO);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> update(@NotBlank(message = "Id necesario") @PathVariable final String id, @Valid @RequestBody final FrequencyRequestDTO frequencyRequestDTO){
        return frequencyService.update(id, frequencyRequestDTO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> delete(@NotBlank(message = "Id necesario") @PathVariable final String id){
        return frequencyService.delete(id);
    }
}

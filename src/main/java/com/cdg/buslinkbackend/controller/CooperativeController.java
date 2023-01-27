package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.model.request.cooperative.CooperativeRequestDTO;
import com.cdg.buslinkbackend.model.request.cooperative.CooperativeWithFrequenciesRequestDTO;
import com.cdg.buslinkbackend.service.cooperative.ICooperativeService;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/protected/cooperatives")
public class CooperativeController {

    private final ICooperativeService cooperativeService;

    public CooperativeController(ICooperativeService cooperativeService) {
        this.cooperativeService = cooperativeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAll() {
        return cooperativeService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable final String id) {
        return cooperativeService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(
            @RequestParam("name") @NotBlank(message = "El nombre es obligatorio") String name,
            @RequestParam("phone") @NotBlank(message = "El teléfono es obligatorio") String phone,
            @RequestParam("address") @NotBlank(message = "La dirección es obligatoria") String address,
            @RequestParam(value = "max") @NotEmpty(message = "La cantidad máxima de buses de la cooperativa no puede estar vacia.")
            @Min(value = 1, message = "La cantidad mínima permitida por cada cooperativa es de 1") Integer max,
            @RequestParam(value = "image", required = false) MultipartFile image

    ) throws IOException {
        return cooperativeService.save(new CooperativeRequestDTO(name, phone, address, max, image));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable final String id,
            @RequestParam("name")
            @NotBlank(message = "El nombre es obligatorio")
            String name,
            @RequestParam("phone")
            @NotBlank(message = "El teléfono es obligatorio") String phone,

            @RequestParam("address") @NotBlank(message = "La dirección es obligatoria") String address,
            @NotEmpty(message = "La cantidad máxima de buses de la cooperativa no puede estar vacia.")
            @Size(min = 1, message = "La cantidad mínima permitida por cada cooperativa es de 1")
            @RequestParam("max") Integer max,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "status") Boolean status
    ) throws IOException {
        return cooperativeService.update(id, new CooperativeRequestDTO(name, phone, address, max, status, image));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable final String id) {
        return cooperativeService.delete(id);
    }


    @PutMapping(value = "/frequencies")
    public ResponseEntity<ApiResponse> saveFrequencies(@RequestBody final CooperativeWithFrequenciesRequestDTO  requestDTO){
        return  cooperativeService.saveFrequencies(requestDTO);
    }
}

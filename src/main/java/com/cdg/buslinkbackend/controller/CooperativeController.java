package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.model.request.cooperative.CooperativeRequestDTO;
import com.cdg.buslinkbackend.service.cooperative.ICooperativeService;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import jakarta.validation.constraints.NotBlank;
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
    public ResponseEntity<ApiResponse> findAll(){
        return cooperativeService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable final String id){
        return cooperativeService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(
            @NotBlank(message = "El nombre es obligatorio")
            @RequestParam("name") String name,
            @NotBlank(message = "El teléfono es obligatorio")
            @RequestParam("phone") String phone,
            @NotBlank(message = "La dirección es obligatoria")
            @RequestParam("address") String address,
            @RequestParam(value = "image", required = false)MultipartFile image
            ) throws IOException {
        return cooperativeService.save(new CooperativeRequestDTO(name, phone, address, image));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable final String id,
            @NotBlank(message = "El nombre es obligatorio")
            @RequestParam("name") String name,
            @NotBlank(message = "El teléfono es obligatorio")
            @RequestParam("phone") String phone,
            @NotBlank(message = "La dirección es obligatoria")
            @RequestParam("address") String address,
            @RequestParam(value = "image", required = false)MultipartFile image
    ) throws IOException {
        return cooperativeService.update(id,new CooperativeRequestDTO(name, phone, address, image));
    }

    @PutMapping(value = "/{id}/change")
    public ResponseEntity<ApiResponse> changeStatus(@PathVariable final String id){
        return cooperativeService.changeStatus(id);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable final String id){
        return cooperativeService.delete(id);
    }

}

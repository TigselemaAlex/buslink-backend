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

/**
 * It's a controller that handles requests to the /protected/cooperatives
 * endpoint.
 */
@RestController
@RequestMapping(value = "/protected/cooperatives")
public class CooperativeController {

    private final ICooperativeService cooperativeService;

    // A constructor.
    public CooperativeController(ICooperativeService cooperativeService) {
        this.cooperativeService = cooperativeService;
    }

    /**
     * This function returns a response entity that contains an API response that
     * contains a list of
     * cooperatives.
     * 
     * @return ResponseEntity&lt;ApiResponse&gt;
     */
    @GetMapping
    public ResponseEntity<ApiResponse> findAll() {
        return cooperativeService.findAll();
    }

    /**
     * It returns a response entity with an api response object
     * 
     * @param id the id of the cooperative
     * @return ResponseEntity<ApiResponse>
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable final String id) {
        return cooperativeService.findById(id);
    }

    /**
     * It receives a request with a name, phone, address, max and image, and returns
     * a response with a
     * name, phone, address, max and image
     * 
     * @param name    String
     * @param phone   String
     * @param address String
     * @param max     The maximum number of buses that the cooperative can have.
     * @param image   MultipartFile
     * @return A ResponseEntity with a ApiResponse object.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> save(
            @RequestParam("name") @NotBlank(message = "El nombre es obligatorio") String name,
            @RequestParam("phone") @NotBlank(message = "El teléfono es obligatorio") String phone,
            @RequestParam("address") @NotBlank(message = "La dirección es obligatoria") String address,
            @RequestParam(value = "max") @NotEmpty(message = "La cantidad máxima de buses de la cooperativa no puede estar vacia.") @Min(value = 1, message = "La cantidad mínima permitida por cada cooperativa es de 1") Integer max,
            @RequestParam(value = "image", required = false) MultipartFile image

    ) throws IOException {
        return cooperativeService.save(new CooperativeRequestDTO(name, phone, address, max, image));
    }

    /**
     * It updates a cooperative with the given id, with the given name, phone,
     * address, max, status and
     * image
     * 
     * @param id      The id of the cooperative to be updated
     * @param name    String
     * @param phone   String
     * @param address String
     * @param max     Integer
     * @param image   MultipartFile
     * @param status  Boolean
     * @return A ResponseEntity with a ApiResponse object.
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> update(
            @PathVariable final String id,
            @RequestParam("name") @NotBlank(message = "El nombre es obligatorio") String name,
            @RequestParam("phone") @NotBlank(message = "El teléfono es obligatorio") String phone,

            @RequestParam("address") @NotBlank(message = "La dirección es obligatoria") String address,
            @NotEmpty(message = "La cantidad máxima de buses de la cooperativa no puede estar vacia.") @Size(min = 1, message = "La cantidad mínima permitida por cada cooperativa es de 1") @RequestParam("max") Integer max,
            @RequestParam(value = "image", required = false) MultipartFile image,
            @RequestParam(value = "status") Boolean status) throws IOException {
        return cooperativeService.update(id, new CooperativeRequestDTO(name, phone, address, max, status, image));
    }

    /**
     * It deletes a cooperative by id
     * 
     * @param id The id of the cooperative to be deleted
     * @return A ResponseEntity object.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable final String id) {
        return cooperativeService.delete(id);
    }

    /**
     * It takes a request body of type CooperativeWithFrequenciesRequestDTO and
     * returns a
     * ResponseEntity of type ApiResponse
     * 
     * @param cooperativeWithFrequenciesRequestDTO
     * @return A ResponseEntity with a body of type ApiResponse.
     */
    @PutMapping(value = "/save/frequencies")
    public ResponseEntity<ApiResponse> saveFrequencies(
            @RequestBody final CooperativeWithFrequenciesRequestDTO cooperativeWithFrequenciesRequestDTO) {
        return cooperativeService.saveFrequencies(cooperativeWithFrequenciesRequestDTO);
    }

}

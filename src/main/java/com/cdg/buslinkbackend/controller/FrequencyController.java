package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.model.request.frequency.FrequencyRequestDTO;
import com.cdg.buslinkbackend.service.frecuency.FrequencyService;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * This class is a controller that handles requests to the
 * /protected/frequencies endpoint.
 */
@RestController
@RequestMapping(value = "/protected/frequencies")
public class FrequencyController {

    private final FrequencyService frequencyService;

    // A constructor that is being used to inject the frequencyService into the
    // controller.
    public FrequencyController(FrequencyService frequencyService) {
        this.frequencyService = frequencyService;
    }

    /**
     * This function returns a response entity that contains an api response that
     * contains a list of
     * frequencies.
     * 
     * @return ResponseEntity&lt;ApiResponse&gt;
     */
    @GetMapping
    public ResponseEntity<ApiResponse> findAll() {
        return frequencyService.findAll();
    }

    /**
     * It returns a response entity with an api response object
     * 
     * @param id The id of the frequency to be retrieved
     * @return ResponseEntity<ApiResponse>
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable final String id) {
        return frequencyService.findById(id);
    }

    /**
     * It takes a FrequencyRequestDTO object, validates it, and then passes it to
     * the
     * frequencyService.save() function
     * 
     * @param frequencyRequestDTO This is the object that will be sent to the API.
     * @return ResponseEntity&lt;ApiResponse&gt;
     */
    @PostMapping
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody final FrequencyRequestDTO frequencyRequestDTO) {
        return frequencyService.save(frequencyRequestDTO);
    }

    /**
     * It updates a frequency by id
     * 
     * @param id                  The id of the frequency to be updated
     * @param frequencyRequestDTO This is the object that I want to update.
     * @return ResponseEntity<ApiResponse>
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> update(@NotBlank(message = "Id necesario") @PathVariable final String id,
            @Valid @RequestBody final FrequencyRequestDTO frequencyRequestDTO) {
        return frequencyService.update(id, frequencyRequestDTO);
    }

    /**
     * It deletes a frequency by id
     * 
     * @param id The id of the frequency to be deleted
     * @return ResponseEntity<ApiResponse>
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> delete(@NotBlank(message = "Id necesario") @PathVariable final String id) {
        return frequencyService.delete(id);
    }
}

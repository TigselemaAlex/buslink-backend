package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.model.request.bus.BusRequestDTO;
import com.cdg.buslinkbackend.service.bus.BusService;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * It's a controller class that handles requests to the /protected/buses
 * endpoint.
 */
@RestController
@RequestMapping(value = "/protected/buses")
public class BusController {

    private final BusService busService;

    // A constructor.
    @Autowired
    public BusController(BusService busService) {
        this.busService = busService;
    }

    /**
     * This function is used to save a bus
     * 
     * @param busRequestDTO This is the object that will be sent to the server.
     * @return ResponseEntity&lt;ApiResponse&gt;
     */
    @PostMapping
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody final BusRequestDTO busRequestDTO) {
        return busService.save(busRequestDTO);
    }

    /**
     * It returns a response entity of type ApiResponse, which is a class that
     * contains a list of buses
     * and a message
     * 
     * @param id the id of the cooperative
     * @return A ResponseEntity object
     */
    @GetMapping(value = "/cooperative/{id}")
    public ResponseEntity<ApiResponse> findAllByCooperative(@PathVariable final String id) {
        return busService.findAllByCooperative(id);
    }

    /**
     * It takes a string as a parameter, and returns a response entity of type
     * ApiResponse
     * 
     * @param id The id of the bus
     * @return ResponseEntity<ApiResponse>
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable final String id) {
        return busService.findById(id);
    }

    /**
     * It takes a bus id, and a busRequestDTO, and returns a response entity of an
     * ApiResponse
     * 
     * @param id            The id of the bus you want to update
     * @param busRequestDTO This is the object that will be passed to the service
     *                      layer.
     * @return ResponseEntity<ApiResponse>
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable final String id,
            @RequestBody final BusRequestDTO busRequestDTO) {
        return busService.update(id, busRequestDTO);
    }

    /**
     * It deletes a bus from the database
     * 
     * @param id The id of the bus to be deleted
     * @return A ResponseEntity object.
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable final String id) {
        return busService.delete(id);
    }

    /**
     * It resets the seating of a bus with the given id
     * 
     * @param id The id of the bus
     * @return ResponseEntity<ApiResponse>
     */
    @PutMapping(value = "/reset/{id}")
    public ResponseEntity<ApiResponse> resetSeating(@PathVariable final String id) {
        return busService.resetSeating(id);
    }

}

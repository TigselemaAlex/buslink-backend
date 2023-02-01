package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.model.request.itinerary.ItineraryRequestDTO;
import com.cdg.buslinkbackend.model.request.itinerary.ItinerarySearchDTO;
import com.cdg.buslinkbackend.service.itinerary.ItineraryService;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * It receives a JSON object, and if it's null, it returns a list of all
 * itineraries, otherwise it
 * returns a list of itineraries that match the origin and/or destination
 */
@RestController
@RequestMapping(value = "/protected/itineraries")
public class ItineraryController {

    private final ItineraryService itineraryService;

    // Injecting the ItineraryService into the ItineraryController.
    @Autowired
    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    // A method that receives a JSON object and returns a list of itineraries that
    // match the origin
    // and/or destination.
    @PostMapping
    public ResponseEntity<ApiResponse> save(@RequestBody final ItineraryRequestDTO itineraryRequestDTO) {
        return itineraryService.save(itineraryRequestDTO);
    }

    /**
     * It receives a JSON object, and if it's null, it returns a list of all
     * itineraries, otherwise it
     * returns a list of itineraries that match the origin and/or destination
     * 
     * @param itinerarySearchDTO ItinerarySearchDTO
     * @return ResponseEntity<ApiResponse>
     */
    @PostMapping(value = "/find")
    public ResponseEntity<ApiResponse> find(
            @RequestBody(required = false) final ItinerarySearchDTO itinerarySearchDTO) {
        if (Objects.isNull(itinerarySearchDTO)) {
            return itineraryService.findByOrigenOrDestiny(null, null);
        }
        return itineraryService.findByOrigenOrDestiny(itinerarySearchDTO.getOrigen(), itinerarySearchDTO.getDestiny());
    }
}

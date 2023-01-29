package com.cdg.buslinkbackend.controller;

import com.cdg.buslinkbackend.model.request.itinerary.ItineraryRequestDTO;
import com.cdg.buslinkbackend.model.request.itinerary.ItinerarySearchDTO;
import com.cdg.buslinkbackend.service.itinerary.ItineraryService;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping (value = "/protected/itineraries")
public class ItineraryController {

    private final ItineraryService itineraryService;

    @Autowired
    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(@RequestBody final ItineraryRequestDTO itineraryRequestDTO){
        return itineraryService.save(itineraryRequestDTO);
    }

    @PostMapping ()
    public ResponseEntity<ApiResponse> find (@RequestBody(required = false) final ItinerarySearchDTO itinerarySearchDTO){
        if(Objects.isNull(itinerarySearchDTO)){
            return itineraryService.findByOrigenOrDestiny(null, null);
        }
        return itineraryService.findByOrigenOrDestiny(itinerarySearchDTO.getOrigen(), itinerarySearchDTO.getDestiny());
    }
}

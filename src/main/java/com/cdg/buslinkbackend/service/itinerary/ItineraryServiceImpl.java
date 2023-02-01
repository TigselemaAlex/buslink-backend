package com.cdg.buslinkbackend.service.itinerary;

import com.cdg.buslinkbackend.exception.BusNotFoundException;
import com.cdg.buslinkbackend.exception.FrequencyNotFoundException;
import com.cdg.buslinkbackend.model.entity.Bus;
import com.cdg.buslinkbackend.model.entity.Frequency;
import com.cdg.buslinkbackend.model.entity.Itinerary;
import com.cdg.buslinkbackend.model.mappers.ItineraryMapper;
import com.cdg.buslinkbackend.model.request.itinerary.ItineraryRequestDTO;
import com.cdg.buslinkbackend.model.response.itinerary.ItineraryResponseDTO;
import com.cdg.buslinkbackend.repository.BusRepository;
import com.cdg.buslinkbackend.repository.FrequencyRespository;
import com.cdg.buslinkbackend.repository.ItineraryRepository;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import com.cdg.buslinkbackend.util.response.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * It takes a list of frequencies and a bus, and creates an itinerary for each
 * frequency and bus
 */
@Service
@Transactional
public class ItineraryServiceImpl implements ItineraryService {

    private final ItineraryRepository itineraryRepository;

    private final BusRepository busRepository;

    private final FrequencyRespository frequencyRespository;

    private final ResponseBuilder responseBuilder;

    // A constructor.
    @Autowired
    public ItineraryServiceImpl(ItineraryRepository itineraryRepository, BusRepository busRepository,
            FrequencyRespository frequencyRespository, ResponseBuilder responseBuilder) {
        this.itineraryRepository = itineraryRepository;
        this.busRepository = busRepository;
        this.frequencyRespository = frequencyRespository;
        this.responseBuilder = responseBuilder;
    }

    /**
     * It takes a list of frequencies and a bus, and creates an itinerary for each
     * frequency and bus
     * 
     * @param itineraryRequestDTO ItineraryRequestDTO
     * @return A list of itineraries
     */
    @Override
    public ResponseEntity<ApiResponse> save(ItineraryRequestDTO itineraryRequestDTO) {
        Bus bus = busRepository.findById(itineraryRequestDTO.getBus_id())
                .orElseThrow(() -> new BusNotFoundException(itineraryRequestDTO.getBus_id()));

        if (itineraryRequestDTO.getFrequencies().isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(),
                    "No se han enviado las frecuencias a asignar");
        }

        List<Itinerary> itineraries = itineraryRequestDTO.getFrequencies().stream().map(
                itineraryFrequencyDetailDTO -> {
                    Frequency frequency = frequencyRespository.findById(itineraryFrequencyDetailDTO.getFrequency_id())
                            .orElseThrow(() -> new FrequencyNotFoundException(
                                    itineraryFrequencyDetailDTO.getFrequency_id()));
                    Optional<Itinerary> itineraryOptional = itineraryRepository.findByBusAndFrequencyAndDepartureTime(
                            bus, frequency, itineraryFrequencyDetailDTO.getDepartureTime());
                    return itineraryOptional.orElseGet(
                            () -> new Itinerary(bus, frequency, itineraryFrequencyDetailDTO.getDepartureTime()));
                }).toList();

        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Itinerario creado exitosamente",
                itineraryRepository.saveAll(itineraries));
    }

    /**
     * It returns a list of itineraries filtered by origin or destiny, if both are
     * null, it returns all the
     * itineraries
     * 
     * @param origen  String
     * @param destiny String
     * @return A ResponseEntity<ApiResponse>
     */

    @Override
    public ResponseEntity<ApiResponse> findByOrigenOrDestiny(String origen, String destiny) {
        List<Itinerary> itineraries = itineraryRepository.findAll();
        List<Itinerary> filter;
        if (Objects.nonNull(origen) && Objects.isNull(destiny)) {
            filter = itineraries.stream().filter(
                    itinerary -> itinerary.getFrequency().getOrigen().equals(origen)).toList();
        } else if (Objects.nonNull(destiny) && Objects.isNull(origen)) {
            filter = itineraries.stream().filter(
                    itinerary -> itinerary.getFrequency().getDestiny().equals(destiny)).toList();
        } else if (Objects.nonNull(destiny)) {
            filter = itineraries.stream().filter(
                    itinerary -> itinerary.getFrequency().getDestiny().equals(destiny)
                            && itinerary.getFrequency().getOrigen().equals(origen))
                    .toList();
        } else {

            List<ItineraryResponseDTO> itineraryResponseDTOS = itineraries.stream()
                    .map(ItineraryMapper::itineraryResponseDTOFromItinerary).toList();
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Listado de itinerarios",
                    itineraryResponseDTOS);
        }
        List<ItineraryResponseDTO> itineraryResponseDTOS = filter.stream()
                .map(ItineraryMapper::itineraryResponseDTOFromItinerary).toList();
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Listado de itinerarios filtrados",
                itineraryResponseDTOS);
    }
}

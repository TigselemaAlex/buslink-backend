package com.cdg.buslinkbackend.service.dashboard;

import com.cdg.buslinkbackend.model.entity.Cooperative;
import com.cdg.buslinkbackend.repository.CooperativeRepository;
import com.cdg.buslinkbackend.repository.FrequencyRespository;
import com.cdg.buslinkbackend.repository.UserRepository;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import com.cdg.buslinkbackend.util.response.ResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This class is a service class that implements the DashboardService interface
 */
@Service

@Transactional
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;

    private final CooperativeRepository cooperativeRepository;

    private final FrequencyRespository frequencyRespository;

    private final ResponseBuilder responseBuilder;

    // A constructor.
    public DashboardServiceImpl(UserRepository userRepository, CooperativeRepository cooperativeRepository,
            FrequencyRespository frequencyRespository, ResponseBuilder responseBuilder) {
        this.userRepository = userRepository;
        this.cooperativeRepository = cooperativeRepository;
        this.frequencyRespository = frequencyRespository;
        this.responseBuilder = responseBuilder;
    }

    /**
     * It returns a response entity with the status code 200, a message and the
     * number of users in the
     * database
     * 
     * @return A ResponseEntity object.
     */
    @Override
    public ResponseEntity<ApiResponse> countUsers() {

        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Cantidad de usuarios", userRepository.count());
    }

    /**
     * It returns a response entity with the status code 200, a message and the
     * count of the
     * cooperatives
     * 
     * @return The responseBuilder.buildResponse() method returns a
     *         ResponseEntity<ApiResponse> object.
     */
    @Override
    public ResponseEntity<ApiResponse> countCooperatives() {
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Cantidad de cooperativas",
                cooperativeRepository.count());
    }

    /**
     * It returns a response entity with the status code 200, a message, and the
     * count of the
     * frequencies
     * 
     * @return A ResponseEntity object.
     */
    @Override
    public ResponseEntity<ApiResponse> countFrequencies() {
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Cantidad de frecuencias",
                frequencyRespository.count());
    }

    /**
     * It counts the number of buses that don't have a frequency
     * 
     * @return A ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> countCooperativesWithoutFrequencies() {
        List<Cooperative> cooperatives = cooperativeRepository.findAll();
        AtomicLong count = new AtomicLong(0);
        cooperatives.forEach(cooperative -> {
            if (cooperative.getFrequencies().isEmpty()) {
                count.getAndIncrement();
            }
        });
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Cantidad de Buses sin Frecuencia", count);
    }
}

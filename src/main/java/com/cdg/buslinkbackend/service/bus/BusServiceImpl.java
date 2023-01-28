package com.cdg.buslinkbackend.service.bus;

import com.cdg.buslinkbackend.exception.CooperativeNotFoundException;
import com.cdg.buslinkbackend.model.entity.Bus;
import com.cdg.buslinkbackend.model.entity.Cooperative;
import com.cdg.buslinkbackend.model.entity.Itinerary;
import com.cdg.buslinkbackend.model.entity.Seating;
import com.cdg.buslinkbackend.model.enums.SeatingType;
import com.cdg.buslinkbackend.model.mappers.BusMapper;
import com.cdg.buslinkbackend.model.request.bus.BusRequestDTO;
import com.cdg.buslinkbackend.model.response.bus.BusResponseDTO;
import com.cdg.buslinkbackend.repository.BusRepository;
import com.cdg.buslinkbackend.repository.CooperativeRepository;
import com.cdg.buslinkbackend.util.response.ApiResponse;
import com.cdg.buslinkbackend.util.response.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Service
@Transactional
public class BusServiceImpl implements BusService{

    private final BusRepository busRepository;

    private final CooperativeRepository cooperativeRepository;

    private final ResponseBuilder responseBuilder;

    @Autowired
    public BusServiceImpl(BusRepository busRepository, CooperativeRepository cooperativeRepository, ResponseBuilder responseBuilder) {
        this.busRepository = busRepository;
        this.cooperativeRepository = cooperativeRepository;
        this.responseBuilder = responseBuilder;
    }

    @Override
    public ResponseEntity<ApiResponse> save(BusRequestDTO busRequestDTO) {

        if (busRepository.existsByBusNumber(busRequestDTO.getBusNumber())) {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "El numero del bus ya esta asignado");
        }

        if(Objects.isNull(busRequestDTO.getVipPrice())){
            busRequestDTO.setEndVip(0);
            busRequestDTO.setStartVip(0);
        }

        if (Objects.isNull(busRequestDTO.getStartVip()) && Objects.isNull(busRequestDTO.getEndVip())) {
            busRequestDTO.setEndVip(0);
            busRequestDTO.setStartVip(0);
        }

        if(busRequestDTO.getStartVip() > busRequestDTO.getSeatingNumber() || busRequestDTO.getEndVip()> busRequestDTO.getSeatingNumber()){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "El inicio o fin de los asientos VIP no puede exceder la cantidad de asientos que posee el bus");
        }

        Cooperative cooperative = cooperativeRepository.findById(busRequestDTO.getCooperative_id()).orElseThrow(() -> new CooperativeNotFoundException(busRequestDTO.getCooperative_id()));

        if(busRepository.countByCooperative(cooperative) == cooperative.getMax() ){
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "No se pueden registrar mas buses, capicidad máxima alcanzada.");
        }

        List<Seating> seatingList = new ArrayList<>();
        for (int i = 1; i <= busRequestDTO.getSeatingNumber(); i++) {
            seatingList.add(
                    new Seating(i, (i >= busRequestDTO.getStartVip() && i <= busRequestDTO.getEndVip()) ? SeatingType.VIP : SeatingType.NORMAL
                    )
            );
        }
        for (Itinerary it : busRequestDTO.getItineraries()) {
            it.setAvailable_seats(
                    IntStream.range(1, busRequestDTO.getSeatingNumber() + 1).boxed().toList()
            );
        }


        Bus bus = BusMapper.busFromBusRequestDTO(busRequestDTO,cooperative, seatingList);

        bus = busRepository.save(bus);
        BusResponseDTO busResponseDTO = BusMapper.busResponseDTOFromBus(bus);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Bus registrado exitosamente", busResponseDTO);
    }

    @Override
    public ResponseEntity<ApiResponse> findAllByCooperative(String cooperative_id) {
        Cooperative cooperative = cooperativeRepository.findById(cooperative_id).orElseThrow( () -> new CooperativeNotFoundException(cooperative_id));
        List<Bus> buses = busRepository.findAllByCooperative(cooperative);
        if(buses.isEmpty()){
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "No hay buses que mostrar");
        }
        /*List<BusResponseDTO> responseDTOS = buses.stream().map(BusMapper::busResponseDTOFromBus).toList();*/
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Listado de buses de la cooperativa: ".concat(cooperative.getName()), buses);
    }


    @Override
    public ResponseEntity<ApiResponse> findById(String id) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> findAllByOrigen(String origen) {


        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> findAllByDestiny(String destiny) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> findAllByOrigenAndDestiny(String origen, String destiny) {
        return null;
    }


}

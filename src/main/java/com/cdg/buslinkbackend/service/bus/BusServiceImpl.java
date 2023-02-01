package com.cdg.buslinkbackend.service.bus;

import com.cdg.buslinkbackend.exception.BusNotFoundException;
import com.cdg.buslinkbackend.exception.CooperativeNotFoundException;
import com.cdg.buslinkbackend.model.entity.Bus;
import com.cdg.buslinkbackend.model.entity.Cooperative;
import com.cdg.buslinkbackend.model.entity.Seating;
import com.cdg.buslinkbackend.model.enums.SeatingStatus;
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

/**
 * This class is a service class that implements the BusService interface.
 */
@Service
@Transactional
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    private final CooperativeRepository cooperativeRepository;

    private final ResponseBuilder responseBuilder;

    // A constructor.
    @Autowired
    public BusServiceImpl(BusRepository busRepository, CooperativeRepository cooperativeRepository,
            ResponseBuilder responseBuilder) {
        this.busRepository = busRepository;
        this.cooperativeRepository = cooperativeRepository;
        this.responseBuilder = responseBuilder;
    }

    /**
     * The function is used to save a bus in the database, it checks if the bus
     * number already exists,
     * if the vip price is null, if the vip start and end are null, if the vip start
     * and end are
     * greater than the seating number, if the cooperative has reached its maximum
     * capacity of buses,
     * and if the bus is saved successfully, it returns a response
     * 
     * @param busRequestDTO This is the object that is sent to the API.
     * @return A ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> save(BusRequestDTO busRequestDTO) {

        if (busRepository.existsByBusNumber(busRequestDTO.getBusNumber())) {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "El numero del bus ya esta asignado");
        }

        if (Objects.isNull(busRequestDTO.getVipPrice())) {
            busRequestDTO.setEndVip(0);
            busRequestDTO.setStartVip(0);
        }

        if (Objects.isNull(busRequestDTO.getStartVip()) && Objects.isNull(busRequestDTO.getEndVip())) {
            busRequestDTO.setEndVip(0);
            busRequestDTO.setStartVip(0);
        }

        if (busRequestDTO.getStartVip() > busRequestDTO.getSeatingNumber()
                || busRequestDTO.getEndVip() > busRequestDTO.getSeatingNumber()) {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(),
                    "El inicio o fin de los asientos VIP no puede exceder la cantidad de asientos que posee el bus");
        }

        Cooperative cooperative = cooperativeRepository.findById(busRequestDTO.getCooperative_id())
                .orElseThrow(() -> new CooperativeNotFoundException(busRequestDTO.getCooperative_id()));

        if (busRepository.countByCooperative(cooperative) == cooperative.getMax()) {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(),
                    "No se pueden registrar mas buses, capicidad máxima alcanzada.");
        }

        List<Seating> seatingList = createSeating(busRequestDTO);

        Bus bus = BusMapper.busFromBusRequestDTO(busRequestDTO, cooperative, seatingList);

        bus = busRepository.save(bus);
        BusResponseDTO busResponseDTO = BusMapper.busResponseDTOFromBus(bus);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Bus registrado exitosamente", busResponseDTO);
    }

    /**
     * It creates a list of seatings, each seating has a number, a type (VIP or
     * NORMAL) and a status
     * (LIBRE or RESERVED)
     * 
     * @param busRequestDTO This is the object that contains the parameters that are
     *                      passed to the API.
     * @return A list of Seating objects.
     */
    private List<Seating> createSeating(BusRequestDTO busRequestDTO) {
        List<Seating> seatingList = new ArrayList<>();
        for (int i = 1; i <= busRequestDTO.getSeatingNumber(); i++) {
            seatingList.add(
                    new Seating(i,
                            (i >= busRequestDTO.getStartVip() && i <= busRequestDTO.getEndVip()) ? SeatingType.VIP
                                    : SeatingType.NORMAL,
                            SeatingStatus.LIBRE));
        }
        return seatingList;
    }

    /**
     * It returns a list of buses that belong to a cooperative
     * 
     * @param cooperative_id String
     * @return ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> findAllByCooperative(String cooperative_id) {
        Cooperative cooperative = cooperativeRepository.findById(cooperative_id)
                .orElseThrow(() -> new CooperativeNotFoundException(cooperative_id));
        List<Bus> buses = busRepository.findAllByCooperative(cooperative);
        if (buses.isEmpty()) {
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "No hay buses que mostrar");
        }
        List<BusResponseDTO> responseDTOS = buses.stream().map(BusMapper::busResponseDTOFromBus).toList();
        return responseBuilder.buildResponse(HttpStatus.OK.value(),
                "Listado de buses de la cooperativa: ".concat(cooperative.getName()), responseDTOS);
    }

    /**
     * It finds a bus by its id, if it doesn't exist, it throws an exception, if it
     * does exist, it maps
     * the bus to a busResponseDTO and returns a response with the busResponseDTO
     * 
     * @param id The id of the bus
     * @return A ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> findById(String id) {
        Bus bus = busRepository.findById(id).orElseThrow(() -> new BusNotFoundException(id));
        BusResponseDTO busResponseDTO = BusMapper.busResponseDTOFromBus(bus);
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Bus encontrado", busResponseDTO);
    }

    /**
     * The function is supposed to update a bus, but it's not working as expected
     * 
     * @param id            String
     * @param busRequestDTO
     * @return A ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> update(String id, BusRequestDTO busRequestDTO) {

        if (Objects.isNull(busRequestDTO.getVipPrice())) {
            busRequestDTO.setEndVip(0);
            busRequestDTO.setStartVip(0);
        }

        if (Objects.isNull(busRequestDTO.getStartVip()) && Objects.isNull(busRequestDTO.getEndVip())) {
            busRequestDTO.setEndVip(0);
            busRequestDTO.setStartVip(0);
        }

        if (busRequestDTO.getStartVip() > busRequestDTO.getSeatingNumber()
                || busRequestDTO.getEndVip() > busRequestDTO.getSeatingNumber()) {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(),
                    "El inicio o fin de los asientos VIP no puede exceder la cantidad de asientos que posee el bus");
        }
        Bus bus = busRepository.findById(id).orElseThrow(() -> new BusNotFoundException(id));
        Bus busComprovation = busRepository.findByBusNumber(busRequestDTO.getBusNumber())
                .orElseThrow(() -> new BusNotFoundException("Bus Number " + busRequestDTO.getBusNumber()));
        if (bus.getId().equals(busComprovation.getId())) {
            bus.setBusNumber(busRequestDTO.getBusNumber());
        } else {
            return responseBuilder.buildResponse(HttpStatus.BAD_REQUEST.value(), "Ya existe un bus con ese número");
        }
        bus.setBrand(busRequestDTO.getBrand());
        bus.setChassis(busRequestDTO.getChassis());
        bus.setModel(busRequestDTO.getModel());
        bus.setSeatingNumber(busRequestDTO.getSeatingNumber());
        bus.setVipPrice(busRequestDTO.getVipPrice());
        bus.setPlate(busRequestDTO.getPlate());

        List<Seating> seatingList = createSeating(busRequestDTO);
        for (int i = 0; i < seatingList.size(); i++) {
            if (bus.getSeating().size() - 1 == i) {
                break;
            }
            seatingList.get(i).setStatus(bus.getSeating().get(i).getStatus());
        }
        bus.setSeating(seatingList);
        bus = busRepository.save(bus);
        BusResponseDTO busResponseDTO = BusMapper.busResponseDTOFromBus(bus);
        return responseBuilder.buildResponse(HttpStatus.CREATED.value(), "Bus actualizado exitosamente",
                busResponseDTO);
    }

    /**
     * If the bus exists, delete it, otherwise throw an exception
     * 
     * @param id The id of the bus to be deleted
     * @return A ResponseEntity object.
     */
    @Override
    public ResponseEntity<ApiResponse> delete(String id) {
        if (busRepository.existsById(id)) {
            busRepository.deleteById(id);
            return responseBuilder.buildResponse(HttpStatus.OK.value(), "Bus eliminado exitosamente");
        }
        throw new BusNotFoundException(id);
    }

    /**
     * It takes a bus id, finds the bus, sets the status of all the seats to LIBRE,
     * saves the bus and
     * returns the bus
     * 
     * @param id String
     * @return A ResponseEntity<ApiResponse>
     */
    @Override
    public ResponseEntity<ApiResponse> resetSeating(String id) {
        Bus bus = busRepository.findById(id).orElseThrow(() -> new BusNotFoundException(id));
        bus.setSeating(bus.getSeating().stream().peek(
                seating -> seating.setStatus(SeatingStatus.LIBRE)).toList());
        bus = busRepository.save(bus);
        BusResponseDTO busResponseDTO = BusMapper.busResponseDTOFromBus(bus);
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "Bus reseteado", busResponseDTO);
    }

}

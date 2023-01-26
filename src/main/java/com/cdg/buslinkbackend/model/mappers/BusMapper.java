package com.cdg.buslinkbackend.model.mappers;

import com.cdg.buslinkbackend.model.entity.Bus;
import com.cdg.buslinkbackend.model.entity.Cooperative;
import com.cdg.buslinkbackend.model.entity.Seating;
import com.cdg.buslinkbackend.model.request.bus.BusRequestDTO;
import com.cdg.buslinkbackend.model.response.bus.BusResponseDTO;

import java.util.List;

public class BusMapper {
    public static Bus busFromBusRequestDTO(BusRequestDTO busRequestDTO, Cooperative cooperative, List<Seating> seatingList){
            return Bus.builder()
                    .brand(busRequestDTO.getBrand())
                    .model(busRequestDTO.getModel())
                    .plate(busRequestDTO.getPlate())
                    .chassis(busRequestDTO.getChassis())
                    .busNumber(busRequestDTO.getBusNumber())
                    .seatingNumber(busRequestDTO.getSeatingNumber())
                    .vipPrice(busRequestDTO.getVipPrice())
                    .itineraries(busRequestDTO.getItineraries())
                    .cooperative(cooperative)
                    .seating(seatingList)
                    .build();
    }

    public static BusResponseDTO busResponseDTOFromBus(Bus bus){
        return BusResponseDTO.builder()
                .id(bus.getId())
                .brand(bus.getBrand())
                .model(bus.getModel())
                .plate(bus.getPlate())
                .chassis(bus.getChassis())
                .busNumber(bus.getBusNumber())
                .seating(bus.getSeating())
                .vipPrice(bus.getVipPrice())
                .itineraries(bus.getItineraries())
                .cooperative(CooperativeMapper
                        .cooperativeResponseDTOFromCooperative(bus.getCooperative()))
                .build();
    }
}

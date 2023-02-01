package com.cdg.buslinkbackend.model.mappers;

import com.cdg.buslinkbackend.model.entity.Bus;
import com.cdg.buslinkbackend.model.entity.Cooperative;
import com.cdg.buslinkbackend.model.entity.Seating;
import com.cdg.buslinkbackend.model.request.bus.BusRequestDTO;
import com.cdg.buslinkbackend.model.response.bus.BusResponseDTO;

import java.util.List;

/**
 * It takes a BusRequestDTO, a Cooperative and a List of Seating and returns a
 * Bus
 */
public class BusMapper {
        /**
         * It takes a BusRequestDTO, a Cooperative and a List of Seating and returns a
         * Bus
         * 
         * @param busRequestDTO This is the object that contains the data that will be
         *                      used to create the
         *                      bus object.
         * @param cooperative   Cooperative object
         * @param seatingList   List of Seating objects
         * @return A Bus object
         */
        public static Bus busFromBusRequestDTO(BusRequestDTO busRequestDTO, Cooperative cooperative,
                        List<Seating> seatingList) {
                return Bus.builder()
                                .brand(busRequestDTO.getBrand())
                                .model(busRequestDTO.getModel())
                                .plate(busRequestDTO.getPlate())
                                .chassis(busRequestDTO.getChassis())
                                .busNumber(busRequestDTO.getBusNumber())
                                .seatingNumber(busRequestDTO.getSeatingNumber())
                                .vipPrice(busRequestDTO.getVipPrice())
                                .cooperative(cooperative)
                                .seating(seatingList)
                                .build();
        }

        /**
         * It takes a bus object and returns a busResponseDTO object
         * 
         * @param bus Bus
         * @return A BusResponseDTO object.
         */
        public static BusResponseDTO busResponseDTOFromBus(Bus bus) {
                return BusResponseDTO.builder()
                                .id(bus.getId())
                                .brand(bus.getBrand())
                                .model(bus.getModel())
                                .plate(bus.getPlate())
                                .chassis(bus.getChassis())
                                .busNumber(bus.getBusNumber())
                                .seating(bus.getSeating())
                                .vipPrice(bus.getVipPrice())
                                .cooperative(CooperativeMapper
                                                .cooperativeResponseDTOFromCooperative(bus.getCooperative()))
                                .build();
        }
}

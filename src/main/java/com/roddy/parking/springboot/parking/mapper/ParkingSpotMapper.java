package com.roddy.parking.springboot.parking.mapper;

import com.roddy.parking.springboot.parking.DTO.ParkingSpotDTO;
import com.roddy.parking.springboot.parking.entities.ParkingSpot;
import org.springframework.stereotype.Component;

@Component
public class ParkingSpotMapper {

    public ParkingSpot toEntity(ParkingSpotDTO dto){
        ParkingSpot parkingSpot = new ParkingSpot();
        parkingSpot.setId(dto.getId());
        parkingSpot.setState(dto.getState());
        parkingSpot.setType(dto.getType());

        return parkingSpot;
    }

    public ParkingSpotDTO toDto(ParkingSpot parkingSpot){
        ParkingSpotDTO dto = new ParkingSpotDTO();
        dto.setId(parkingSpot.getId());
        dto.setState(parkingSpot.getState());
        dto.setType(dto.getType());

        return dto;
    }
}

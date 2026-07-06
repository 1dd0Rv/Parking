package com.roddy.parking.springboot.parking.mapper;

import com.roddy.parking.springboot.parking.DTO.RecordDTO;
import com.roddy.parking.springboot.parking.entities.Record;
import org.springframework.stereotype.Component;

@Component
public class RecordMapper {

    public RecordDTO toDto(Record record){
        RecordDTO dto = new RecordDTO();
        dto.setId(record.getId());
        dto.setLicensePlate(record.getVehicle().getLicensePlate());
        dto.setParkingSpotId(record.getParkingSpot().getId());
        dto.setEntryTime(record.getEntryTime());
        dto.setExitTime(record.getExitTime());
        dto.setAmount(record.getAmount());

        return dto;
    }
}

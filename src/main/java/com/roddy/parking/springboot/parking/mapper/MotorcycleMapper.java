package com.roddy.parking.springboot.parking.mapper;

import com.roddy.parking.springboot.parking.DTO.MotorcycleDTO;
import com.roddy.parking.springboot.parking.entities.Motorcycle;
import com.roddy.parking.springboot.parking.enums.VehicleType;
import org.springframework.stereotype.Component;

@Component
public class MotorcycleMapper {
    public Motorcycle toEntity(MotorcycleDTO dto){
        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setLicensePlate(dto.getLicensePlate());
        motorcycle.setBrand(dto.getBrand());
        motorcycle.setModel(dto.getModel());
        motorcycle.setColor(dto.getColor());
        motorcycle.setSidecar(dto.getSidecar());
        motorcycle.setType(VehicleType.MOTORCYCLE);

        return motorcycle;
    }

    public MotorcycleDTO toDto(Motorcycle motor){
        MotorcycleDTO dto = new MotorcycleDTO();
        dto.setLicensePlate(motor.getLicensePlate());
        dto.setBrand(motor.getBrand());
        dto.setModel(motor.getModel());
        dto.setColor(motor.getColor());
        dto.setSidecar(motor.getSidecar());

        return dto;
    }
}

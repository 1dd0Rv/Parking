package com.roddy.parking.springboot.parking.mapper;

import com.roddy.parking.springboot.parking.DTO.CarDTO;
import com.roddy.parking.springboot.parking.entities.Car;
import com.roddy.parking.springboot.parking.enums.VehicleType;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public Car toEntity(CarDTO dto){
        Car car = new Car();
        car.setLicensePlate(dto.getLicensePlate());
        car.setBrand(dto.getBrand());
        car.setModel(dto.getModel());
        car.setColor(dto.getColor());
        car.setDoors(dto.getDoors());
        car.setType(VehicleType.CAR);

        return car;
    }

    public CarDTO toDto(Car car){
        CarDTO dto= new CarDTO();
        dto.setLicensePlate(car.getLicensePlate());
        dto.setBrand(car.getBrand());
        dto.setModel(car.getModel());
        dto.setColor(car.getColor());
        dto.setDoors(car.getDoors());

        return dto;
    }
}

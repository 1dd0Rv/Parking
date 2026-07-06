package com.roddy.parking.springboot.parking.services;

import com.roddy.parking.springboot.parking.entities.Record;
import com.roddy.parking.springboot.parking.entities.Rate;
import com.roddy.parking.springboot.parking.entities.Vehicle;
import com.roddy.parking.springboot.parking.enums.VehicleType;

import java.util.List;
import java.util.Optional;

public interface RecordService {
    List<Record> findAll();
    Optional<Record> findById(Long id);
    Record save(Record r);
    List<Rate> findByVehicle(Vehicle v);
    Record entry(Vehicle vehicle);
    Record exit(String licensePlate);
}

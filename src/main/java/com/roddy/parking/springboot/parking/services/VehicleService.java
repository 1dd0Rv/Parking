package com.roddy.parking.springboot.parking.services;

import com.roddy.parking.springboot.parking.entities.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {

    List<Vehicle> findAll();

    Vehicle findById(Long id);

    Vehicle save(Vehicle v);

    void deleteById(Long id);

    Optional<Vehicle> findByLicensePlate(String licensePlate);
}
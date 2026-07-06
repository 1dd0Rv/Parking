package com.roddy.parking.springboot.parking.repositories;

import com.roddy.parking.springboot.parking.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByLicensePlate(String licensePlate);
}

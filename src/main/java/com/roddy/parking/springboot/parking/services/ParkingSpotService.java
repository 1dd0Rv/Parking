package com.roddy.parking.springboot.parking.services;

import com.roddy.parking.springboot.parking.entities.ParkingSpot;
import com.roddy.parking.springboot.parking.enums.State;
import com.roddy.parking.springboot.parking.enums.VehicleType;

import java.util.List;

public interface ParkingSpotService {
    List<ParkingSpot> findAll();

    ParkingSpot findById(Long id);

    ParkingSpot save(ParkingSpot p);

    void deleteById(Long id);

    List<ParkingSpot> findByTypeAndState(VehicleType type, State state); // <- same

    ParkingSpot update(Long id, ParkingSpot parkingSpot);
}

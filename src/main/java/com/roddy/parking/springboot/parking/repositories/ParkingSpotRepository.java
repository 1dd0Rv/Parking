package com.roddy.parking.springboot.parking.repositories;

import com.roddy.parking.springboot.parking.entities.ParkingSpot;
import com.roddy.parking.springboot.parking.enums.State;
import com.roddy.parking.springboot.parking.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    List<ParkingSpot> findByTypeAndState(VehicleType type, State state);

}

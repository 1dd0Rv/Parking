package com.roddy.parking.springboot.parking.repositories;

import com.roddy.parking.springboot.parking.entities.Record;
import com.roddy.parking.springboot.parking.entities.Rate;
import com.roddy.parking.springboot.parking.entities.Vehicle;
import com.roddy.parking.springboot.parking.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Rate> findByVehicle(Vehicle v);
    Optional<Record> findByVehicleLicensePlateAndExitTimeIsNull(String licensePlate);

}

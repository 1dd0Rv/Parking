package com.roddy.parking.springboot.parking.repositories;

import com.roddy.parking.springboot.parking.entities.Rate;
import com.roddy.parking.springboot.parking.enums.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Long> {
    Optional<Rate> findByType(VehicleType type);
}

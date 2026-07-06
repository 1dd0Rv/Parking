package com.roddy.parking.springboot.parking.services;

import com.roddy.parking.springboot.parking.entities.Rate;
import com.roddy.parking.springboot.parking.enums.VehicleType;

import java.util.List;
import java.util.Optional;

public interface RateService {
    List<Rate> findAll();

    Rate findById(Long id);

    Rate save(Rate t);

    void deleteById(Long id);

    Optional<Rate> findByType(VehicleType v);

    Rate update(Long id, Rate rate);
}

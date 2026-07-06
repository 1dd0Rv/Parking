package com.roddy.parking.springboot.parking.controller;

import com.roddy.parking.springboot.parking.DTO.CarDTO;
import com.roddy.parking.springboot.parking.DTO.MotorcycleDTO;
import com.roddy.parking.springboot.parking.entities.Car;
import com.roddy.parking.springboot.parking.entities.Motorcycle;
import com.roddy.parking.springboot.parking.entities.Vehicle;
import com.roddy.parking.springboot.parking.mapper.CarMapper;
import com.roddy.parking.springboot.parking.mapper.MotorcycleMapper;
import com.roddy.parking.springboot.parking.services.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private VehicleService service;
    private final CarMapper carMapper;
    private final MotorcycleMapper motorcycleMapper;


    public VehicleController(VehicleService service, CarMapper carMapper, MotorcycleMapper motorcycleMapper) {
        this.service = service;
        this.carMapper = carMapper;
        this.motorcycleMapper = motorcycleMapper;
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Vehicle vehicle = service.findById(id);
        return ResponseEntity.ok(vehicle);
    }

    @PostMapping("/cars")
    public ResponseEntity<?> createCar(@RequestBody CarDTO carDTO) {
        Car car = carMapper.toEntity(carDTO);
        Car saved = (Car) service.save(car);
        return ResponseEntity.status(HttpStatus.CREATED).body(carMapper.toDto(saved));
    }

    @PostMapping("/moto")
    public ResponseEntity<?> createMotorcycle(@RequestBody MotorcycleDTO motorcycleDTO) {
        Motorcycle motorcycle = motorcycleMapper.toEntity(motorcycleDTO);
        Motorcycle saved = (Motorcycle) service.save(motorcycle);
        return ResponseEntity.status(HttpStatus.CREATED).body(motorcycleMapper.toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}

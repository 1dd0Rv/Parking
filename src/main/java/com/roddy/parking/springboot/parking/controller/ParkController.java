package com.roddy.parking.springboot.parking.controller;

import com.roddy.parking.springboot.parking.DTO.CarDTO;
import com.roddy.parking.springboot.parking.DTO.MotorcycleDTO;
import com.roddy.parking.springboot.parking.DTO.RecordDTO;
import com.roddy.parking.springboot.parking.entities.Car;
import com.roddy.parking.springboot.parking.entities.Motorcycle;
import com.roddy.parking.springboot.parking.entities.Record;
import com.roddy.parking.springboot.parking.mapper.CarMapper;
import com.roddy.parking.springboot.parking.mapper.MotorcycleMapper;
import com.roddy.parking.springboot.parking.mapper.RecordMapper;
import com.roddy.parking.springboot.parking.services.RecordService;
import com.roddy.parking.springboot.parking.services.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parking")
public class ParkController {

    private final RecordMapper recordMapper;
    private RecordService recordService;
    private VehicleService vehicleService;
    private final CarMapper carMapper;
    private final MotorcycleMapper motorcycleMapper;

    public ParkController(CarMapper carMapper, MotorcycleMapper motorcycleMapper, RecordService recordService, VehicleService vehicleService, RecordMapper recordMapper) {
        this.carMapper = carMapper;
        this.motorcycleMapper = motorcycleMapper;
        this.recordService = recordService;
        this.vehicleService = vehicleService;
        this.recordMapper = recordMapper;
    }

    @PostMapping("/entry/car")
    public ResponseEntity<?> carEntry(@RequestBody CarDTO carDTO) {
        Car newVehicle = carMapper.toEntity(carDTO);          // DTO -> entity (without saving)
        Record record = recordService.entry(newVehicle); // the service decides find/create + parking spot + record
        RecordDTO dto = recordMapper.toDto(record);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/entry/moto")
    public ResponseEntity<?> motorcycleEntry(@RequestBody MotorcycleDTO motorcycleDTO) {
        Motorcycle newVehicle = motorcycleMapper.toEntity(motorcycleDTO);
        Record record = recordService.entry(newVehicle);
        RecordDTO dto = recordMapper.toDto(record);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/exit/{licensePlate}")
    public ResponseEntity<?> exit(@PathVariable String licensePlate) {
        Record record = recordService.exit(licensePlate);
        RecordDTO dto = recordMapper.toDto(record);
        return ResponseEntity.ok(dto);
    }

}

package com.roddy.parking.springboot.parking.controller;

import com.roddy.parking.springboot.parking.DTO.ParkingSpotDTO;
import com.roddy.parking.springboot.parking.entities.ParkingSpot;
import com.roddy.parking.springboot.parking.mapper.ParkingSpotMapper;
import com.roddy.parking.springboot.parking.services.ParkingSpotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking-spots")
public class ParkingSpotController {

    private ParkingSpotService parkingSpotService;
    private ParkingSpotMapper parkingSpotMapper;

    public ParkingSpotController(ParkingSpotMapper parkingSpotMapper, ParkingSpotService parkingSpotService) {
        this.parkingSpotMapper = parkingSpotMapper;
        this.parkingSpotService = parkingSpotService;
    }

    @GetMapping
    public List<ParkingSpotDTO> list(){
        return parkingSpotService.findAll().stream().map(parkingSpotMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id){
        ParkingSpot parkingSpot = parkingSpotService.findById(id);

        return ResponseEntity.ok(parkingSpotMapper.toDto(parkingSpot));
    }

    @PostMapping
    public ResponseEntity<?> createParkingSpot(@RequestBody ParkingSpotDTO parkingSpotDto){
        ParkingSpot parkingSpot = parkingSpotMapper.toEntity(parkingSpotDto);
        ParkingSpot saved = parkingSpotService.save(parkingSpot);

        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ParkingSpotDTO parkingSpotDto){
        ParkingSpot parkingSpot = parkingSpotMapper.toEntity(parkingSpotDto);
        ParkingSpot updated = parkingSpotService.update(id, parkingSpot);

        return ResponseEntity.ok(parkingSpotMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        parkingSpotService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

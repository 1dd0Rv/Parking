package com.roddy.parking.springboot.parking.controller;

import com.roddy.parking.springboot.parking.DTO.RateDTO;
import com.roddy.parking.springboot.parking.entities.Rate;
import com.roddy.parking.springboot.parking.mapper.RateMapper;
import com.roddy.parking.springboot.parking.services.RateService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rates")
public class RateController {

    private RateService rateService;
    private final RateMapper rateMapper;

    public RateController(RateService rateService, RateMapper rateMapper) {
        this.rateService = rateService;
        this.rateMapper = rateMapper;
    }

    @GetMapping
    public List<RateDTO> list() {
        return rateService.findAll().stream().map(rateMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Rate rate = rateService.findById(id);
        return ResponseEntity.ok(rateMapper.toDto(rate));
    }

    @PostMapping
    public ResponseEntity<?> createRate(@RequestBody RateDTO rateDTO) {
        Rate rate = rateMapper.toEntity(rateDTO);     // DTO -> entity
        Rate saved = rateService.save(rate);        // save
        return ResponseEntity.status(HttpStatus.CREATED).body(rateMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody RateDTO rateDTO) {
        Rate rate = rateMapper.toEntity(rateDTO);
        Rate updated = rateService.update(id, rate);

        return ResponseEntity.ok(rateMapper.toDto(updated));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        rateService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

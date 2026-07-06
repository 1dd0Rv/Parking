package com.roddy.parking.springboot.parking.services;

import com.roddy.parking.springboot.parking.entities.Vehicle;
import com.roddy.parking.springboot.parking.exceptions.ResourceNotFoundException;
import com.roddy.parking.springboot.parking.repositories.VehicleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {


    private VehicleRepository repository;

    public VehicleServiceImpl(VehicleRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Vehicle> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Vehicle findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe un vehiculo con este id"));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("No existe un vehiculo con este id");
        }
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public Vehicle save(Vehicle v) {
        return repository.save(v);

    }


    @Transactional(readOnly = true)
    @Override
    public Optional<Vehicle> findByLicensePlate(String licensePlate) {
        return repository.findByLicensePlate(licensePlate);
    }
}

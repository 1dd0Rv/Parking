package com.roddy.parking.springboot.parking.services;

import com.roddy.parking.springboot.parking.entities.ParkingSpot;
import com.roddy.parking.springboot.parking.enums.State;
import com.roddy.parking.springboot.parking.enums.VehicleType;
import com.roddy.parking.springboot.parking.exceptions.ResourceNotFoundException;
import com.roddy.parking.springboot.parking.repositories.ParkingSpotRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ParkingSpotServiceImpl implements ParkingSpotService {


    private ParkingSpotRepository repository;

    public ParkingSpotServiceImpl(ParkingSpotRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ParkingSpot> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public ParkingSpot findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe una plaza con ese id"));
    }

    @Transactional
    @Override
    public ParkingSpot save(ParkingSpot p) {
        return repository.save(p);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("No existe plaza con este id");
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ParkingSpot> findByTypeAndState(VehicleType type, State state) {
        return repository.findByTypeAndState(type, state);
    }

    @Transactional
    @Override
    public ParkingSpot update(Long id, ParkingSpot parkingSpot) {
        ParkingSpot existingSpot = repository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("No existe una plaza con ese id"));

        existingSpot.setType(parkingSpot.getType());
        existingSpot.setState(parkingSpot.getState());

        return repository.save(existingSpot);
    }
}

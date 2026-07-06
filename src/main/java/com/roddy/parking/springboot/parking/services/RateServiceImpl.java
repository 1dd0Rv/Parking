package com.roddy.parking.springboot.parking.services;

import com.roddy.parking.springboot.parking.entities.Rate;
import com.roddy.parking.springboot.parking.enums.VehicleType;
import com.roddy.parking.springboot.parking.exceptions.ResourceNotFoundException;
import com.roddy.parking.springboot.parking.repositories.RateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RateServiceImpl implements RateService {


    private RateRepository repository;

    public RateServiceImpl(RateRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Rate> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Rate findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No existe una tarifa con ese id"));
    }

    @Transactional
    @Override
    public Rate save(Rate t) {
        return repository.save(t);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("No existe una tarifa con ese id");
        }
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Rate> findByType(VehicleType v) {
        return repository.findByType(v);
    }

    @Transactional
    @Override
    public Rate update(Long id, Rate rate) {
        Rate existingRate = repository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("No existe una tarifa con ese id"));

        existingRate.setType(rate.getType());
        existingRate.setPricePerDay(rate.getPricePerDay());
        existingRate.setPricePerHour(rate.getPricePerHour());
        existingRate.setPricePerMinute(rate.getPricePerMinute());

        return repository.save(existingRate);
    }


}

package com.roddy.parking.springboot.parking.services;

import com.roddy.parking.springboot.parking.entities.ParkingSpot;
import com.roddy.parking.springboot.parking.entities.Record;
import com.roddy.parking.springboot.parking.entities.Rate;
import com.roddy.parking.springboot.parking.entities.Vehicle;
import com.roddy.parking.springboot.parking.enums.State;
import com.roddy.parking.springboot.parking.enums.VehicleType;
import com.roddy.parking.springboot.parking.exceptions.ParkingSpotNotAvailableException;
import com.roddy.parking.springboot.parking.exceptions.ResourceNotFoundException;
import com.roddy.parking.springboot.parking.repositories.RecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;


@Service
public class RecordServiceImpl implements RecordService {

    private final RecordRepository repository;
    private final ParkingSpotService parkingSpotService;
    private final RateService rateService;
    private final VehicleService vehicleService;

    public RecordServiceImpl(RecordRepository repository, ParkingSpotService parkingSpotService, RateService rateService, VehicleService vehicleService) {
        this.repository = repository;
        this.parkingSpotService = parkingSpotService;
        this.rateService = rateService;
        this.vehicleService = vehicleService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Record> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Record> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public Record save(Record r) {
        return repository.save(r);
    }

    @Transactional
    @Override
    public List<Rate> findByVehicle(Vehicle v) {
        return repository.findByVehicle(v);
    }

    @Transactional
    @Override
    public Record entry(Vehicle vehicle) {
        // (1) Check parking spots BEFORE touching anything - fail fast
        List<ParkingSpot> freeSpots = parkingSpotService.findByTypeAndState(vehicle.getType(), State.FREE);
        if (freeSpots.isEmpty()) {
            throw new ParkingSpotNotAvailableException("No hay plazas disponibles para este tipo de vehículo");
        }

        // (2) Find or create the vehicle: reuse it if it already exists by license plate, otherwise save it
        Vehicle finalVehicle = vehicleService.findByLicensePlate(vehicle.getLicensePlate())
                .orElseGet(() -> vehicleService.save(vehicle));

        // (3) Occupy the first free parking spot
        ParkingSpot assignedSpot = freeSpots.get(0);
        assignedSpot.setState(State.OCCUPIED);
        parkingSpotService.save(assignedSpot);

        // (4) Create the record with the final vehicle
        Record newRecord = new Record();
        newRecord.setVehicle(finalVehicle);
        newRecord.setParkingSpot(assignedSpot);
        newRecord.setEntryTime(LocalDateTime.now());

        return repository.save(newRecord);
    }

    private double calculateAmount(LocalDateTime entryTime, LocalDateTime exitTime, Rate rate) {
        long minutes = ChronoUnit.MINUTES.between(entryTime, exitTime);
        long hours = ChronoUnit.HOURS.between(entryTime, exitTime);
        long days = ChronoUnit.DAYS.between(entryTime, exitTime);

        if (days >= 1) {
            return days * rate.getPricePerDay();
        } else if (hours >= 1) {
            return hours * rate.getPricePerHour();
        } else {
            return minutes * rate.getPricePerMinute();
        }
    }

    @Transactional
    @Override
    public Record exit(String licensePlate) {
        Record isIn = repository.findByVehicleLicensePlateAndExitTimeIsNull(licensePlate)
                .orElseThrow(() -> new ResourceNotFoundException("La matrícula del vehículo no ha sido registrada"));

        isIn.setExitTime(LocalDateTime.now());

        VehicleType vehicleType = isIn.getVehicle().getType();

        Rate rate = rateService.findByType(vehicleType)
                .orElseThrow(() -> new ResourceNotFoundException("No existe tarifa para este tipo de vehículo"));

        double amount = calculateAmount(isIn.getEntryTime(), isIn.getExitTime(), rate);
        isIn.setAmount(amount);

        ParkingSpot parkingSpot = isIn.getParkingSpot();
        parkingSpot.setState(State.FREE);
        parkingSpotService.save(parkingSpot);

        return repository.save(isIn);
    }
}

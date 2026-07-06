package com.roddy.parking.springboot.parking.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "parking_record")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ParkingSpot parkingSpot;

    @ManyToOne
    private Rate rate;

    @ManyToOne
    private Vehicle vehicle;

    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    private Double amount;

    public Record(ParkingSpot parkingSpots, Rate rates, Vehicle vehicles) {
        this.parkingSpot = parkingSpots;
        this.rate = rates;
        this.vehicle = vehicles;
    }

    public Record() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpots) {
        this.parkingSpot = parkingSpots;
    }

    public Rate getRate() {
        return rate;
    }

    public void setRate(Rate rates) {
        this.rate = rates;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicles) {
        this.vehicle = vehicles;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(LocalDateTime entryTime) {
        this.entryTime = entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void setExitTime(LocalDateTime exitTime) {
        this.exitTime = exitTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}

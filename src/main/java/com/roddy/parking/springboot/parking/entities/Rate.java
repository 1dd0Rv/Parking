package com.roddy.parking.springboot.parking.entities;

import com.roddy.parking.springboot.parking.enums.VehicleType;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    private Double pricePerMinute;
    private Double pricePerHour;
    private Double pricePerDay;

    @OneToMany(mappedBy = "rate")
    private List<Record> record;

    public Rate(VehicleType type, Double pricePerMinute, Double pricePerHour, Double pricePerDay) {
        this.type = type;
        this.pricePerMinute = pricePerMinute;
        this.pricePerHour = pricePerHour;
        this.pricePerDay = pricePerDay;

    }

    public Rate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public Double getPricePerMinute() {
        return pricePerMinute;
    }

    public void setPricePerMinute(Double pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }

    public Double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(Double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public List<Record> getRecord() {
        return record;
    }

    public void setRecord(List<Record> record) {
        this.record = record;
    }
}

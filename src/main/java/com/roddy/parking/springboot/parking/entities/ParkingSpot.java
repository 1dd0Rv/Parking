package com.roddy.parking.springboot.parking.entities;

import com.roddy.parking.springboot.parking.enums.State;
import com.roddy.parking.springboot.parking.enums.VehicleType;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class ParkingSpot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private VehicleType type;

    @Enumerated(EnumType.STRING)
    private State state;

    @OneToMany(mappedBy = "parkingSpot")
    private List<Record> record;

    public ParkingSpot(VehicleType type, State state) {
        this.type = type;
        this.state = state;
    }

    public ParkingSpot() {
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

    public List<Record> getRecord() {
        return record;
    }

    public void setRecord(List<Record> records) {
        this.record = records;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}

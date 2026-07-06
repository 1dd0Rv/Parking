package com.roddy.parking.springboot.parking.DTO;

import com.roddy.parking.springboot.parking.enums.State;
import com.roddy.parking.springboot.parking.enums.VehicleType;

public class ParkingSpotDTO {

    private Long id;
    private VehicleType type;
    private State state;

    public ParkingSpotDTO() {
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
